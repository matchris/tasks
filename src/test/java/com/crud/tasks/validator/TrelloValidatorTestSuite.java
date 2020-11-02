package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;

import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.validator.TrelloValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTestSuite {
    @Mock
    private TrelloValidator trelloValidator;

    @Test
    public void testValidateCard() {
        //Given
        TrelloCard trelloCard1 = new TrelloCard("Card1", "Description", "3rd", "id1");
        TrelloCard trelloCard2 = new TrelloCard("Card2", "Description", "bottom", "id2");
        //When
        trelloValidator.validateCard(trelloCard1);
        trelloValidator.validateCard(trelloCard2);
        //Then
        verify(trelloValidator, times(1)).validateCard(trelloCard1);
        verify(trelloValidator, times(1)).validateCard(trelloCard2);
    }

    @Test
    public void testValidateTrelloBoards() {
        //Given
        TrelloList trello1 = new TrelloList("1", "list name", true);
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(trello1);
        TrelloBoard trelloBoard1 = new TrelloBoard("id1", "name1", trelloList);
        TrelloBoard trelloBoard2 = new TrelloBoard("id2", "name2", trelloList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);
        TrelloValidator trelloValidator = new TrelloValidator();
        //When
        List<TrelloBoard> validatedList = trelloValidator.validateTrelloBoards(trelloBoardList);
        //Then
        assertNotNull(validatedList);
        assertEquals(2, validatedList.size());
        assertEquals("id1", validatedList.get(0).getId());
        assertEquals("name2", validatedList.get(1).getName());
    }

}
