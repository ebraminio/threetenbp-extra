/*
 * Copyright (c) 2013, Ebrahim Byagowi
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.extra.chrono;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.threeten.bp.DateTimeException;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Month;
import org.threeten.bp.chrono.ChronoLocalDate;
import org.threeten.bp.chrono.Chronology;
import org.threeten.bp.temporal.TemporalAdjusters;

/**
 * Test.
 */
@Test
public class TestPersianChrono {

    //-----------------------------------------------------------------------
    // Chrono.ofName("Persian")  Lookup by name
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_chrono_byName() {
        Chronology Persian = Chronology.of("Persian");
        Assert.assertNotNull(Persian, "The Persian calendar could not be found byName");
        Assert.assertEquals(Persian.getId(), "Persian", "Name mismatch");
    }

    //-----------------------------------------------------------------------
    // creation, toLocalDate()
    //-----------------------------------------------------------------------
    @DataProvider(name="samples")
    Object[][] data_samples() {
        return new Object[][] {
            {PersianChronology.INSTANCE.date(1, 1, 1), LocalDate.of(284, 8, 29)},
            {PersianChronology.INSTANCE.date(1, 1, 2), LocalDate.of(284, 8, 30)},
            {PersianChronology.INSTANCE.date(1, 1, 3), LocalDate.of(284, 8, 31)},

            {PersianChronology.INSTANCE.date(2, 1, 1), LocalDate.of(285, 8, 29)},
            {PersianChronology.INSTANCE.date(3, 1, 1), LocalDate.of(286, 8, 29)},
            {PersianChronology.INSTANCE.date(3, 13, 6), LocalDate.of(287, 8, 29)},
            {PersianChronology.INSTANCE.date(4, 1, 1), LocalDate.of(287, 8, 30)},
            {PersianChronology.INSTANCE.date(4, 7, 3), LocalDate.of(288, 2, 28)},
            {PersianChronology.INSTANCE.date(4, 7, 4), LocalDate.of(288, 2, 29)},
            {PersianChronology.INSTANCE.date(5, 1, 1), LocalDate.of(288, 8, 29)},
            {PersianChronology.INSTANCE.date(1662, 3, 3), LocalDate.of(1945, 11, 12)},
            {PersianChronology.INSTANCE.date(1728, 10, 28), LocalDate.of(2012, 7, 5)},
            {PersianChronology.INSTANCE.date(1728, 10, 29), LocalDate.of(2012, 7, 6)},
        };
    }

    @Test(dataProvider="samples", groups={"tck"})
    public void test_toLocalDate(ChronoLocalDate<?> persian, LocalDate iso) {
        assertEquals(LocalDate.from(persian), iso);
    }

    @Test(dataProvider="samples", groups={"tck"})
    public void test_fromCalendrical(ChronoLocalDate<?> persian, LocalDate iso) {
        assertEquals(PersianChronology.INSTANCE.date(iso), persian);
    }

    @DataProvider(name="badDates")
    Object[][] data_badDates() {
        return new Object[][] {
            {1728, 0, 0},

            {1728, -1, 1},
            {1728, 0, 1},
            {1728, 14, 1},
            {1728, 15, 1},

            {1728, 1, -1},
            {1728, 1, 0},
            {1728, 1, 31},
            {1728, 1, 32},

            {1728, 12, -1},
            {1728, 12, 0},
            {1728, 12, 31},
            {1728, 12, 32},

            {1728, 13, -1},
            {1728, 13, 0},
            {1728, 13, 6},
            {1728, 13, 7},

            {1727, 13, -1},
            {1727, 13, 0},
            {1727, 13, 7},
            {1727, 13, 8},
        };
    }

    @Test(dataProvider="badDates", groups={"tck"}, expectedExceptions=DateTimeException.class)
    public void test_badDates(int year, int month, int dom) {
        PersianChronology.INSTANCE.date(year, month, dom);
    }

    //-----------------------------------------------------------------------
    // with(WithAdjuster)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_adjust1() {
        ChronoLocalDate<?> base = PersianChronology.INSTANCE.date(1728, 10, 29);
        ChronoLocalDate<?> test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(test, PersianChronology.INSTANCE.date(1728, 10, 30));
    }

    @Test(groups={"tck"})
    public void test_adjust2() {
        ChronoLocalDate<?> base = PersianChronology.INSTANCE.date(1728, 13, 2);
        ChronoLocalDate<?> test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(test, PersianChronology.INSTANCE.date(1728, 13, 5));
    }

    //-----------------------------------------------------------------------
    // PersianDate.with(Local*)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_adjust_toLocalDate() {
        ChronoLocalDate<?> persian = PersianChronology.INSTANCE.date(1726, 1, 4);
        ChronoLocalDate<?> test = persian.with(LocalDate.of(2012, 7, 6));
        assertEquals(test, PersianChronology.INSTANCE.date(1728, 10, 29));
    }

    @Test(groups={"tck"}, expectedExceptions=DateTimeException.class)
    public void test_adjust_toMonth() {
        ChronoLocalDate<?> persian = PersianChronology.INSTANCE.date(1726, 1, 4);
        persian.with(Month.APRIL);
    }

    //-----------------------------------------------------------------------
    // LocalDate.with(PersianDate)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_LocalDate_adjustToPersianDate() {
        ChronoLocalDate<?> persian = PersianChronology.INSTANCE.date(1728, 10, 29);
        LocalDate test = LocalDate.MIN.with(persian);
        assertEquals(test, LocalDate.of(2012, 7, 6));
    }

    @Test(groups={"tck"})
    public void test_LocalDateTime_adjustToPersianDate() {
        ChronoLocalDate<?> persian = PersianChronology.INSTANCE.date(1728, 10, 29);
        LocalDateTime test = LocalDateTime.MIN.with(persian);
        assertEquals(test, LocalDateTime.of(2012, 7, 6, 0, 0));
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @DataProvider(name="toString")
    Object[][] data_toString() {
        return new Object[][] {
            {PersianChronology.INSTANCE.date(1, 1, 1), "Persian AM 1-01-01"},
            {PersianChronology.INSTANCE.date(1728, 10, 28), "Persian AM 1728-10-28"},
            {PersianChronology.INSTANCE.date(1728, 10, 29), "Persian AM 1728-10-29"},
            {PersianChronology.INSTANCE.date(1727, 13, 5), "Persian AM 1727-13-05"},
            {PersianChronology.INSTANCE.date(1727, 13, 6), "Persian AM 1727-13-06"},
        };
    }

    @Test(dataProvider="toString", groups={"tck"})
    public void test_toString(ChronoLocalDate<?> persian, String expected) {
        assertEquals(persian.toString(), expected);
    }

}
