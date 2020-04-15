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
package usecase.ijoic.ktx.test

import com.ijoic.ktx.util.orCreate
import org.junit.Test
import usecase.ijoic.ktx.entity.User
import usecase.ijoic.ktx.util.assertExist
import usecase.ijoic.ktx.util.assertNotExist
import usecase.ijoic.ktx.util.assertProperty

/**
 * Assert test.
 *
 * @author verstsiu on 2018/6/12.
 * @version 1.0
 */
class AssertTest {
  @Test
  fun testKotlinAssert() {
    val user = User()
    user.name = "Tony"
    user.age = 11

    user.assertExist {
      assertProperty(this::name, "Tony")
      assertProperty(this::age, 11)
    }
  }

  @Test
  fun testJunitAssert() {
    val user: User? = null

    user.assertNotExist()
  }

  @Test
  fun testJunitAssert2() {
    val user: User? = null

    user.orCreate { User() }.assertExist()
  }
}