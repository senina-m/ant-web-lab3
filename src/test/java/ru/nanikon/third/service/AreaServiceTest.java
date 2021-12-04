package ru.nanikon.third.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nanikon.third.entity.ShotEntity;
import ru.nanikon.third.entity.UserEntity;
import ru.nanikon.third.service.model.HorizontalRect;
import ru.nanikon.third.service.model.LittleCircle;
import ru.nanikon.third.service.model.Quarter;
import ru.nanikon.third.service.model.Rhomb;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Natalia Nikonova
 */
class AreaServiceTest {
    private AreaService areaService;
    private UserEntity owner;

    @BeforeEach
    public void setUp() {
        areaService = new AreaService();
        areaService.addShape(new Rhomb(Quarter.FIRST));
        areaService.addShape(new HorizontalRect(Quarter.SECOND));
        areaService.addShape(new LittleCircle(Quarter.THIRD));
        owner = new UserEntity("test-session-id");
    }

    @Test
    void checkArea_whenPointNotInTriangleAndInFirstQuarter_thenIsHitFalse() {
        double x = 1;
        double y = 1;
        int r = 1;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertFalse(shot.isHit());
    }

    @Test
    void checkArea_whenPointInTriangleAndInFirstQuarter_thenIsHitTrue() {
        double x = 1;
        double y = 0;
        int r = 1;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertTrue(shot.isHit());
    }

    @Test
    void checkArea_whenPointNotInRectAndInSecondQuarter_thenIsHitFalse() {
        double x = -1;
        double y = 2;
        int r = 2;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertFalse(shot.isHit());
    }

    @Test
    void checkArea_whenPointInRectAndInSecondQuarter_thenIsHitTrue() {
        double x = -1;
        double y = 0.5;
        int r = 2;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertTrue(shot.isHit());
    }

    @Test
    void checkArea_whenPointNotInCircleAndInThirdQuarter_thenIsHitFalse() {
        double x = -2;
        double y = -2;
        int r = 2;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertFalse(shot.isHit());
    }

    @Test
    void checkArea_whenPointInCircleAndInThirdQuarter_thenIsHitTrue() {
        double x = -1;
        double y = -1;
        int r = 4;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertTrue(shot.isHit());
    }

    @Test
    void checkArea_whenPointInFourthQuarter_thenIsHitFalse() {
        double x = -2;
        double y = -2;
        int r = 2;
        ShotEntity shot = new ShotEntity(owner, x, y, r);
        areaService.checkArea(shot);
        assertFalse(shot.isHit());
    }
}