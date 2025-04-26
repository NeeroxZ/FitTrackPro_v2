package util;

import core.domain.exercise.*;
import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.domain.workout.*;
import core.domain.workout.plans.GanzkoerperPlan;
import core.domain.workout.plans.IWorkoutPlan;
import core.domain.workout.plans.OberUnterPlan;
import core.domain.workout.plans.PushPullLegPlan;
import core.ports.repository.InMemoryExerciseRepositoryMock;
import core.ports.repository.InMemoryWorkoutRepositoryMock;
import core.ports.repository.InMemoryUserRepositoryMock;
import core.ports.session.InMemoryUserSessionServiceMock;
import core.ports.workout.IRandomWorkoutGenerator;

import java.time.LocalDate;
import java.util.List;

public final class TestObjectBuilder {

    public final InMemoryWorkoutRepositoryMock workoutRepo = new InMemoryWorkoutRepositoryMock();
    public final InMemoryExerciseRepositoryMock exerciseRepo = new InMemoryExerciseRepositoryMock();
    public final InMemoryUserSessionServiceMock sessionMock = new InMemoryUserSessionServiceMock();
    public final InMemoryUserRepositoryMock userRepo = new InMemoryUserRepositoryMock();
   public  final List<IWorkoutPlan> plans = List.of(
            new PushPullLegPlan(),
            new OberUnterPlan(),
            new GanzkoerperPlan()
                                                   );
    public IRandomWorkoutGenerator workoutGeneratorStub = (name, type, split, freq, user) ->
            List.of(new Workout(1, name, type, List.of(), user, freq, split));

    private User testUser;

    public TestObjectBuilder withWorkoutGenerator(IRandomWorkoutGenerator generator) {
        this.workoutGeneratorStub = generator;
        return this;
    }

    public TestObjectBuilder withDefaultUser() {
        this.testUser = defaultUser();
        userRepo.add(testUser);
        sessionMock.setLoggedInUser(this.testUser);
        return withUser(testUser);
    }

    public TestObjectBuilder withUser(User user) {
        sessionMock.setLoggedInUser(user);
        return this;
    }

    public TestObjectBuilder withDefaultExercises() {
        List.of(
                new Exercise(1, "Bench Press", ExerciseCategory.BRUST, Difficulty.MEDIUM, "Langhantel drücken.", List.of(MuscleGroup.CHEST)),
                new Exercise(2, "Squats", ExerciseCategory.BEINE, Difficulty.HARD, "Kniebeugen.", List.of(MuscleGroup.LEGS))
               ).forEach(exerciseRepo::addExercise);
        return this;
    }

    public TestObjectBuilder withExercise(Exercise e) {
        exerciseRepo.addExercise(e);
        return this;
    }

    public TestObjectBuilder withWorkout(Workout w) {
        workoutRepo.saveWorkout(w);
        return this;
    }

    public void clearAll() {
        workoutRepo.clear();
        exerciseRepo.clear();
        sessionMock.logout();
    }

    public User getTestUser() {
        return testUser;
    }

    /*
    *pw always dummy
     */
    public User makeUser(String username) {
        return new User(
                username,
                Password.ofHashed("dummy"),
                70.0,
                1.75,
                new Birthday(LocalDate.of(2000, 1, 1))
        );
    }

    private static User defaultUser() {
        return new User(
                "testuser",
                Password.ofHashed("9f86d081884c7d659a2feaa0c55ad015"),
                75.0,
                1.80,
                new Birthday(LocalDate.of(1990, 5, 15))
        );
    }
}
