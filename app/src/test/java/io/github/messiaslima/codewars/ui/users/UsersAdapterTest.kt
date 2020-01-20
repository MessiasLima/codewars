package io.github.messiaslima.codewars.ui.users

import android.view.View
import androidx.test.core.app.ApplicationProvider
import io.github.messiaslima.codewars.databinding.ListItemUserBinding
import io.github.messiaslima.codewars.entity.User
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UsersAdapterTest {

    @Test
    fun userViewHolder_getValidName_with_name_provided(){
        val binding = mock(ListItemUserBinding::class.java)
        `when`(binding.root).thenReturn(View(ApplicationProvider.getApplicationContext()))
        val viewHolder = UsersAdapter.UsersViewHolder(binding)
        val testName = "Test name"
        val testUsername = "testusername"
        val user = User(
            name = testName,
            username = testUsername
        )
        assertEquals(viewHolder.getValidName(user), testName)
    }

    @Test
    fun userViewHolder_getValidName_with_name_not_provided(){

        val binding = mock(ListItemUserBinding::class.java)

        `when`(binding.root).thenReturn(View(ApplicationProvider.getApplicationContext()))

        val viewHolder = UsersAdapter.UsersViewHolder(binding)

        val testName:String? = null
        val testUsername = "testusername"

        val user = User(
            name = testName,
            username = testUsername
        )
        assertEquals(viewHolder.getValidName(user), testUsername)
    }
}