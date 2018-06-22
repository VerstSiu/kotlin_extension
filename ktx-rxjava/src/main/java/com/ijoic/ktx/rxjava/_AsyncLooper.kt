/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.ktx.rxjava

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Execute expected action.
 *
 * @param scheduler scheduler.
 * @param action action.
 * @return execute disposable.
 */
fun execute(scheduler: Scheduler, action: Runnable): Disposable {
  return scheduler.scheduleDirect(action)
}

/**
 * Execute expected action.
 *
 * @param scheduler scheduler.
 * @param action action.
 * @return execute disposable.
 */
fun execute(scheduler: Scheduler, action: () -> Unit): Disposable {
  return scheduler.scheduleDirect(action)
}

/**
 * Execute fromAction and deliver result to toAction.
 *
 * @param fromAction from action.
 * @param toAction to action.
 * @param from from scheduler.
 * @param to to scheduler.
 */
fun<T> execute(fromAction: () -> T, toAction: (T) -> Unit, from: Scheduler = Schedulers.io(), to: Scheduler = AndroidSchedulers.mainThread()) {
  from.scheduleDirect {
    val result = fromAction.invoke()
    to.scheduleDirect { toAction.invoke(result) }
  }
}

/**
 * Execute expected action on io thread.
 *
 * @param action action.
 * @return execute disposable.
 */
fun io(action: Runnable) = execute(Schedulers.io(), action)

/**
 * Execute expected action on io thread.
 *
 * @param action action.
 * @return execute disposable.
 */
fun io(action: () -> Unit) = execute(Schedulers.io(), action)

/**
 * Execute expected action on computation thread.
 *
 * @param action action.
 * @return execute disposable.
 */
fun compute(action: Runnable) = execute(Schedulers.computation(), action)

/**
 * Execute expected action on computation thread.
 *
 * @param action action.
 * @return execute disposable.
 */
fun compute(action: () -> Unit) = execute(Schedulers.computation(), action)