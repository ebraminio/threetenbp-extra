package org.threeten.extra.chrono;

import static org.threeten.bp.temporal.ChronoField.ERA;

import java.util.Locale;

import org.threeten.bp.DateTimeException;
import org.threeten.bp.chrono.Era;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.Temporal;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.TemporalQueries;
import org.threeten.bp.temporal.TemporalQuery;
import org.threeten.bp.temporal.ValueRange;

/**
 * An era in the Persian calendar system.
 * <p>
 * The Persian calendar system has two eras.
 * The date {@code 0001-01-01 (Persian)} is {@code 622-03-22 (ISO)}.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code PersianEra}.
 * Use {@code getValue()} instead.</b>
 *
 * <h3>Specification for implementors</h3>
 * This is an immutable and thread-safe enum.
 */
public enum PersianEra implements Era  {

    /**
     * The singleton instance for the era before the current one, 'Before Anno Hegirae',
     * which has the value 0.
     */
    BEFORE_AM,
    /**
     * The singleton instance for the current era, 'Anno Hegirae', which has the value 1.
     */
    AM;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code HijrahEra} from a value.
     * <p>
     * The current era (from ISO date 622-03-22 onwards) has the value 1
     * The previous era has the value 0.
     *
     * @param era  the era to represent, from 0 to 1
     * @return the PersianEra singleton, never null
     * @throws DateTimeException if the era is invalid
     */
    public static PersianEra of(int era) {
        switch (era) {
            case 0:
                return BEFORE_AM;
            case 1:
                return AM;
            default:
                throw new DateTimeException("PersianEra not valid");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the era numeric value.
     * <p>
     * The current era (from ISO date 622-03-22 onwards) has the value 1.
     * The previous era has the value 0.
     *
     * @return the era value, from 0 (BEFORE_AH) to 1 (AH)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

    @Override
    public PersianChronology getChronology() {
        return PersianChronology.INSTANCE;
    }

    // JDK8 default methods:
    //-----------------------------------------------------------------------
    @Override
    public PersianDate date(int year, int month, int day) {
        return (PersianDate) getChronology().date(this, year, month, day);
    }

    @Override
    public PersianDate dateYearDay(int year, int dayOfYear) {
        return (PersianDate) getChronology().dateYearDay(this, year, dayOfYear);
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == ERA;
        }
        return field != null && field.isSupportedBy(this);
    }

    @Override
    public ValueRange range(TemporalField field) {
        if (field == ERA) {
            return field.range();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.rangeRefinedBy(this);
    }

    @Override
    public int get(TemporalField field) {
        if (field == ERA) {
            return getValue();
        }
        return range(field).checkValidIntValue(getLong(field), field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (field == ERA) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.getFrom(this);
    }

    //-------------------------------------------------------------------------
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ERA, getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.chronology()) {
            return (R) getChronology();
        }
        return query.queryFrom(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public String getDisplayName(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(ERA, style).toFormatter(locale).format(this);
    }

}
