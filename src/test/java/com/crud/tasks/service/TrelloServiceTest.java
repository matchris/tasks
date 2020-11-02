package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void createTrelloCardtest() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TrelloNameCard", "DescriptionTrelloCard", "top", "id1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("idDto1", "nameDto1", "http://myweb.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("admin@myweb.com");
        //when
        CreatedTrelloCardDto newCreatedCard = trelloService.createTrelloCard(trelloCardDto);
        //then
        assertEquals(createdTrelloCardDto.getName(), newCreatedCard.getName());
        assertEquals(createdTrelloCardDto.getId(), newCreatedCard.getId());
        assertEquals(createdTrelloCardDto.getShortUrl(), newCreatedCard.getShortUrl());
    }

    @Test
    public void fetchTrelloBoardsTest() {
        //given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("ListId1", "list1", false));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("id1", "Board1", trelloLists);
//        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("id2", "Board2", trelloLists);
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoardDto1);
//        trelloBoards.add(trelloBoardDto2);
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //then
        assertNotNull(trelloBoardDtos);
        assertEquals(trelloBoards.size(), trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals(trelloBoards.get(0).getId(), (trelloBoardDto.getId()));
            assertEquals(trelloBoards.get(0).getName(), trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals(trelloBoards.get(0).getLists().get(0).getId(), trelloListDto.getId());
                assertEquals(trelloBoards.get(0).getLists().get(0).isClosed(),trelloListDto.isClosed());
            });
        });
    }
}
