package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

    operator fun plus(rti: RepeatedTimeInterval): MyDate {
        return addTimeIntervals(rti.ti, rti.n)
    }

    operator fun plus(ti: TimeInterval): MyDate {
        return plus(RepeatedTimeInterval(ti, 1))
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(i: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, i)
    }
}

class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(date: MyDate): Boolean {
        return date >= start && date <= endInclusive
    }

    operator fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateIterator(private val dateRange: DateRange) : Iterator<MyDate> {
    private var current = dateRange.start
    override fun hasNext() = current <= dateRange.endInclusive

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}
