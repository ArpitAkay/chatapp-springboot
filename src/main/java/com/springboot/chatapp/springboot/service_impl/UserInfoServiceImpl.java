package com.springboot.chatapp.springboot.service_impl;

import com.springboot.chatapp.springboot.entity.Message;
import com.springboot.chatapp.springboot.entity.UserInfo;
import com.springboot.chatapp.springboot.exception.RESTException;
import com.springboot.chatapp.springboot.model.UserInfoReponse;
import com.springboot.chatapp.springboot.repository.UserInfoRepository;
import com.springboot.chatapp.springboot.service.UserInfoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final String USER_NOT_FOUND = "User not found";
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public UserInfoServiceImpl(
            UserInfoRepository userInfoRepository,
            ModelMapper modelMapper
    ) {
        this.userInfoRepository = userInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserInfo saveUserInfo(
            UserInfo userInfo
    ) {
        Optional<UserInfo> userInfoInDb = userInfoRepository.findByName(userInfo.getName());
        if(userInfoInDb.isPresent()) {
            userInfo = userInfoInDb.get();
            userInfo.setActive(true);
        } else {
            userInfo.setActive(true);
        }
        return userInfoRepository.save(userInfo);
    }

    @Override
    public List<UserInfoReponse> getAllUserInfo(
            int id,
            String name
    ) throws RESTException {
        UserInfo userInfoInDb = userInfoRepository.findById(id)
                .orElseThrow(() -> new RESTException(USER_NOT_FOUND));

        List<UserInfo> userInfoList = userInfoRepository.findAll();

        userInfoList.remove(userInfoInDb);


        List<UserInfoReponse> userInfoReponseList =
                userInfoList.stream().map(userInfo -> modelMapper.map(userInfo, UserInfoReponse.class)).toList();

        return userInfoReponseList.stream().map(userInfoReponse -> {
            Message latestMessage = getLatestMessage(id, userInfoReponse.getId());
            if (latestMessage != null) {
                userInfoReponse.setLatestMessage(latestMessage.getContent());
                userInfoReponse.setLatestMessageTime(latestMessage.getTimestamp());
            } else {
                userInfoReponse.setLatestMessage(null);
                userInfoReponse.setLatestMessageTime(null);
            }
            return userInfoReponse;
        }).toList();
    }

    private Message getLatestMessage(
            int senderId,
            int receiverId
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);

        Root<Message> root = criteriaQuery.from(Message.class);
        criteriaQuery.select(root);

        Predicate senderReceiverCondition = criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("senderId"), senderId),
                        criteriaBuilder.equal(root.get("receiverId"), receiverId)
                ),
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("receiverId"), senderId),
                        criteriaBuilder.equal(root.get("senderId"), receiverId)
                )
        );

        criteriaQuery.where(senderReceiverCondition);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("timestamp")));

        TypedQuery<Message> query = entityManager.createQuery(criteriaQuery).setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserInfo logoutUser(
            int id
    ) throws RESTException {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new RESTException(USER_NOT_FOUND));
       userInfo.setActive(false);
        return  userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateName(
            int id,
            Map<String, Object> updatedUserInfo
    ) throws RESTException {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new RESTException(USER_NOT_FOUND));

        updatedUserInfo.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserInfo.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, userInfo, value);
        });

        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserInfo(
            int id
    ) throws RESTException {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new RESTException(USER_NOT_FOUND));
    }

    @Override
    public String deleteUserInfo(
            int id
    ) throws RESTException {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new RESTException(USER_NOT_FOUND));
        userInfoRepository.delete(userInfo);
        return "User deleted successfully";
    }
}
