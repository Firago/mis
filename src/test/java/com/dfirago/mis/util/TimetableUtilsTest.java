package com.dfirago.mis.util;

import com.dfirago.mis.domain.util.TimetableUtils;
import com.dfirago.mis.web.rest.dto.DayDTO;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by dmfi on 05/06/2016.
 */
public class TimetableUtilsTest {

    @Test
    public void testGenerateEmptyDays() {
        ZonedDateTime from = ZonedDateTime.of(2016, 6, 6, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime to = ZonedDateTime.of(2016, 6, 12, 23, 59, 59, 0, ZoneId.systemDefault());
        List<DayDTO> result = TimetableUtils.generateEmptyDays(from, to);
        assertEquals(7, result.size());
    }
}
