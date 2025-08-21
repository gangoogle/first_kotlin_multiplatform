package org.gangoogle.project.home

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Clock
import org.gangoogle.database.User
import org.gangoogle.project.base.BaseComposeVM
import org.gangoogle.project.base.BaseEffect
import org.gangoogle.project.base.BaseState
import org.gangoogle.project.data.db.DatabaseHelper
import org.gangoogle.project.ext.listObs

class AboutUsViewModel : BaseComposeVM<AboutUsViewModel.State, AboutUsViewModel.Effect>(State()) {

    @Immutable
    class State : BaseState() {
        var userList = listObs<User>()
    }

    sealed class Effect : BaseEffect() {
    }

    fun insertDb() {
        DatabaseHelper.database.userQueries.insert(
            Clock.System.now().toEpochMilliseconds().toString()
        )
        queryDb()
    }

    fun queryDb() {
        val list = DatabaseHelper.database.userQueries.queryAll().executeAsList()
        state.userList.clear()
        state.userList.addAll(list)
    }
}