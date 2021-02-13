package rs.kunpero.slackbot.vacation.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.slack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.slack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.kunpero.slackbot.vacation.entity.VacationInfo;
import rs.kunpero.slackbot.vacation.repository.VacationInfoRepository;

import java.io.IOException;
import java.time.ZoneId;

@Service
@Slf4j
public class UserStatusService {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private final MethodsClient methodsClient;
    private final VacationInfoRepository vacationInfoRepository;

    @Autowired
    public UserStatusService(MethodsClient methodsClient, VacationInfoRepository vacationInfoRepository) {
        this.methodsClient = methodsClient;
        this.vacationInfoRepository = vacationInfoRepository;
    }

    public void changeUserStatus(VacationInfo info) {
        final User.Profile profile = new User.Profile();
        profile.setStatusEmoji(":palm_tree:");
        profile.setStatusText(String.format("On vacation until %s", info.getDateTo().toString()));
        profile.setStatusExpiration(info.getDateTo().plusDays(1).atStartOfDay(DEFAULT_ZONE_ID).toEpochSecond());
        UsersProfileSetRequest request = UsersProfileSetRequest.builder()
                .user(info.getUserId())
                .profile(profile)
                .build();
        try {
            UsersProfileSetResponse response = methodsClient.usersProfileSet(request);
            if (response.isOk()) {
                info.setStatusChanged(true);
                vacationInfoRepository.save(info);
                log.info("Status with id [{}] for user [{}] was updated", info.getId(), info.getUserId());
            } else {
                log.warn("Error during user profile change [{}]", response.getError());
            }
        } catch (IOException | SlackApiException e) {
            log.error("Internal system error", e);
        }
    }
}
