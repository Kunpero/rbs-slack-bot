package rs.kunpero.vacation.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.kunpero.vacation.entity.VacationInfo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VacationInfoRepositoryTest {
    @Autowired
    private VacationInfoRepository vacationInfoRepository;

    @Test
    public void findByUserIdTest() {
        final String userId = "USER0";
        final String substitution = "USER1,USER2";

        List<VacationInfo> userVacations = vacationInfoRepository.findByUserId(userId);
        Assert.assertEquals(1, userVacations.size());


        VacationInfo actualVacation = userVacations.get(0);
        VacationInfo expectedVacation = new VacationInfo(0,
                userId,
                LocalDate.of(2018, Month.JUNE, 16),
                LocalDate.of(2018, Month.JUNE, 18),
                substitution);
        Assert.assertEquals(expectedVacation, actualVacation);
    }

    @Test
    public void saveTest() {
        final String userId = "USER1";
        final String substitution = "USER0,USER2";
        final LocalDate from = LocalDate.of(2018, Month.JUNE, 19);
        final LocalDate to = LocalDate.of(2018, Month.JUNE, 20);

        VacationInfo savedEntity = vacationInfoRepository.save(new VacationInfo(userId, from, to, substitution));
        Assert.assertNotNull(savedEntity);
    }

}
