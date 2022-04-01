package productflavorsample

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProvider {
    companion object {
        fun computation() = Schedulers.computation()
        fun mainThread() = AndroidSchedulers.mainThread()
        fun io() = Schedulers.io()
    }
}