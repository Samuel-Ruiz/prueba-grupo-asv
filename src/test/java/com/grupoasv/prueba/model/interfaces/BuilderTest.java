package com.grupoasv.prueba.model.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BuilderTest<S, T>{

    @Spy
    Builder<S, T> builder = (Builder<S, T>) mock(Builder.class, Mockito.CALLS_REAL_METHODS);

    @Test
    public void buildDaoWithListShouldCallBuildDaoWithElement(){
        List<T> list = new ArrayList<>();
        List<S> response = builder.buildDao(list);

        assertThat(response, is(new ArrayList<>()));
    }

    @Test
    public void buildDtoWithListShouldCallBuildDaoWithElement(){
        List<S> list = new ArrayList<>();
        List<T> response = builder.buildDto(list);

        assertThat(response, is(new ArrayList<>()));
    }
}
