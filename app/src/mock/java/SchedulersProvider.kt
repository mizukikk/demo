package productflavorsample

import io.reactivex.schedulers.Schedulers

class SchedulersProvider {
    companion object {
        fun computation() = Schedulers.trampoline()
        fun mainThread() = Schedulers.trampoline()
        fun io() = Schedulers.trampoline()
    }
}