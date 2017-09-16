package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int){

    operator fun compareTo(other: MyDate):Int {
        return if (year - other.year == 0){
            return if (month - other.month == 0){
                dayOfMonth - other.dayOfMonth
            } else {
                month - other.month
            }
        } else{
            year - other.year
        }
    }
}

operator fun MyDate.plus(interval: TimeInterval): MyDate{
    return addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate{
    return addTimeIntervals(interval.ti, interval.n)
}



operator fun MyDate.rangeTo(other: MyDate): DateRange{
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval{
    return RepeatedTimeInterval(this, n)
}


class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)


class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate>{

    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate>{

        private var current = start

        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): MyDate {
            if (!hasNext()){
                throw NoSuchElementException()
            }

            val toReturn = current
            current = current.nextDay()

            return toReturn
        }

    }

    operator fun contains(date: MyDate): Boolean{
        return date >= start && date < endInclusive
    }

}
