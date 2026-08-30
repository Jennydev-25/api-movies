package dev.jenny.apimovies.releaseyear;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class ReleaseYearEntityTest {

    @Test
    void testReleaseYearEntity_InitializationWithIdAndYear() {
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 1994);

        assertThat(releaseYear, is(instanceOf(ReleaseYearEntity.class)));
        assertThat(releaseYear.getClass().getDeclaredFields().length, is(equalTo(2)));
    }
}