package org.threeten.extra.chrono;

import static org.threeten.bp.temporal.ChronoField.EPOCH_DAY;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.threeten.bp.Clock;
import org.threeten.bp.DateTimeException;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.ChronoLocalDateTime;
import org.threeten.bp.chrono.ChronoZonedDateTime;
import org.threeten.bp.chrono.Chronology;
import org.threeten.bp.chrono.Era;
import org.threeten.bp.jdk8.Jdk8Methods;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.TemporalAccessor;
import org.threeten.bp.temporal.ValueRange;

/**
 * The Persian calendar system.
 * <p>
 * This chronology defines the rules of the Persian calendar system.
 * This calendar system is primarily used in Iran and Afghanistan.
 * Dates are aligned such that {@code 0001AM-01-01 (Persian)} is {@code 0284-08-29 (ISO)}.
 * <p>
 * The fields are defined as follows:
 * <p><ul>
 * <li>era - There are two eras, the current 'Era of the Martyrs' (AM) and the previous era (ERA_ERA_BEFORE_AM).
 * <li>year-of-era - The year-of-era for the current era increases uniformly from the epoch at year one.
 *  For the previous era the year increases from one as time goes backwards.
 * <li>proleptic-year - The proleptic year is the same as the year-of-era for the
 *  current era. For the previous era, years have zero, then negative values.
 * <li>month-of-year - There are 13 months in a Persian year, numbered from 1 to 13.
 * <li>day-of-month - There are 30 days in each of the first 12 Persian months, numbered 1 to 30.
 *  The 13th month has 5 days, or 6 in a leap year, numbered 1 to 5 or 1 to 6.
 * <li>day-of-year - There are 365 days in a standard Persian year and 366 in a leap year.
 *  The days are numbered from 1 to 365 or 1 to 366.
 * <li>leap-year - Leap years occur every 4 years.
 * </ul><p>
 *
 * <h3>Specification for implementors</h3>
 * This class is immutable and thread-safe.
 */
public final class PersianChronology extends Chronology implements Serializable {

	/**
     * Singleton instance of the Persian chronology.
     */
    public static final PersianChronology INSTANCE = new PersianChronology();

    /**
     * Serialization version.
     */
	private static final long serialVersionUID = 7414731895332135129L;
    /**
     * Range of months.
     */
    static final ValueRange MOY_RANGE = ValueRange.of(1, 13);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE = ValueRange.of(1, 5, 30);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE_NONLEAP = ValueRange.of(1, 5);
    /**
     * Range of days.
     */
    static final ValueRange DOM_RANGE_LEAP = ValueRange.of(1, 6);

    /**
     * Restricted constructor.
     */
    private PersianChronology() {
    }

    /**
     * Resolve singleton.
     *
     * @return the singleton instance, not null
     */
    private Object readResolve() {
        return INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the ID of the chronology - 'Persian'.
     * <p>
     * The ID uniquely identifies the {@code Persian}.
     * It can be used to lookup the {@code Persian} using {@link #of(String)}.
     *
     * @return the chronology ID - 'Persian'
     * @see #getCalendarType()
     */
    @Override
    public String getId() {
        return "Persian";
    }

    /**
     * Gets the calendar type of the underlying calendar system - 'persian'.
     * <p>
     * The calendar type is an identifier defined by the
     * <em>Unicode Locale Data Markup Language (LDML)</em> specification.
     * It can be used to lookup the {@code Chrono} using {@link #of(String)}.
     * It can also be used as part of a locale, accessible via
     * {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
     *
     * @return the calendar system type - 'persian'
     * @see #getId()
     */
    @Override
    public String getCalendarType() {
        return "persian";
    }

    //-----------------------------------------------------------------------
    @Override  // override with covariant return type
    public PersianDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return (PersianDate) super.date(era, yearOfEra, month, dayOfMonth);
    }

    @Override  // override with covariant return type
    public PersianDate date(int prolepticYear, int month, int dayOfMonth) {
        return new PersianDate(prolepticYear, month, dayOfMonth);
    }

    @Override  // override with covariant return type
    public PersianDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return (PersianDate) super.dateYearDay(era, yearOfEra, dayOfYear);
    }

    @Override  // override with covariant return type
    public PersianDate dateYearDay(int prolepticYear, int dayOfYear) {
        return new PersianDate(prolepticYear, (dayOfYear - 1) / 30 + 1, (dayOfYear - 1) % 30 + 1);
    }

    //-----------------------------------------------------------------------
    @Override  // override with covariant return type
    public PersianDate date(TemporalAccessor temporal) {
        if (temporal instanceof PersianDate) {
            return (PersianDate) temporal;
        }
        return PersianDate.ofEpochDay(temporal.getLong(EPOCH_DAY));
    }

    @Override  // override with covariant return type
    public ChronoLocalDateTime<PersianDate> localDateTime(TemporalAccessor temporal) {
        return (ChronoLocalDateTime<PersianDate>) super.localDateTime(temporal);
    }

    @Override  // override with covariant return type
    public ChronoZonedDateTime<PersianDate> zonedDateTime(TemporalAccessor temporal) {
        return (ChronoZonedDateTime<PersianDate>) super.zonedDateTime(temporal);
    }

    @Override  // override with covariant return type
    public ChronoZonedDateTime<PersianDate> zonedDateTime(Instant instant, ZoneId zone) {
        return (ChronoZonedDateTime<PersianDate>) super.zonedDateTime(instant, zone);
    }

    //-----------------------------------------------------------------------
    @Override  // override with covariant return type
    public PersianDate dateNow() {
        return (PersianDate) super.dateNow();
    }

    @Override  // override with covariant return type
    public PersianDate dateNow(ZoneId zone) {
        return (PersianDate) super.dateNow(zone);
    }

    @Override  // override with covariant return type
    public PersianDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return (PersianDate) super.dateNow(clock);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified year is a leap year.
     * <p>
     * A Persian proleptic-year is leap if the remainder after division by four equals three.
     * This method does not validate the year passed in, and only has a
     * well-defined result for years in the supported range.
     *
     * @param prolepticYear  the proleptic-year to check, not validated for range
     * @return true if the year is a leap year
     */
    @Override
    public boolean isLeapYear(long prolepticYear) {
        return Jdk8Methods.floorMod(prolepticYear, 4) == 3;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        if (era instanceof PersianEra == false) {
            throw new DateTimeException("Era must be PersianEra");
        }
        return (era == PersianEra.AM ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public Era eraOf(int eraValue) {
        return PersianEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return Arrays.<Era>asList(PersianEra.values());
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(ChronoField field) {
        switch (field) {
            case DAY_OF_MONTH: return ValueRange.of(1, 5, 30);
            case ALIGNED_WEEK_OF_MONTH: return ValueRange.of(1, 1, 5);
            case MONTH_OF_YEAR: return ValueRange.of(1, 13);
            case EPOCH_MONTH: return ValueRange.of(-1000, 1000);  // TODO
            case YEAR_OF_ERA: return ValueRange.of(1, 999, 1000);  // TODO
            case YEAR: return ValueRange.of(-1000, 1000);  // TODO
        }
        return field.range();
    }

}
