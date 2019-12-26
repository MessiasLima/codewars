package io.github.messiaslima.codewars.ui.challenges

import dagger.Component
import io.github.messiaslima.codewars.repository.challenge.ChallengeRepsitoryModule

@Component(modules = [ChallengeRepsitoryModule::class])
interface ChallengeComponent {
    fun inject(challengesViewModel: ChallengesViewModel)
}