package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper mapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListDto1 = new ArrayList<>();
        List<TrelloListDto> trelloListDto2 = Arrays.asList(
                new TrelloListDto("List1", "Monday Education stuff", false),
                new TrelloListDto("list2", "Tuesday Education stuff", false));
        List<TrelloBoardDto> trelloBoardDto = Arrays.asList(
                new TrelloBoardDto("Board1", "Work Stuff", trelloListDto1),
                new TrelloBoardDto("Board2", "Home", trelloListDto1),
                new TrelloBoardDto("Board3", "Education", trelloListDto2));

        //When
        List<TrelloBoard> trelloBoards = mapper.mapToBoards(trelloBoardDto);

        //Then
        assertEquals(3, trelloBoards.size());
        assertEquals("Board2", trelloBoards.get(1).getId());
        assertEquals("Education", trelloBoards.get(2).getName());
        assertEquals(0, trelloBoards.get(0).getLists().size());
        assertEquals(2, trelloBoards.get(2).getLists().size());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> trelloList1 = new ArrayList<>();
        List<TrelloList> trelloList2 = Arrays.asList(
                new TrelloList("List1", "Monday Education Stuff", false),
                new TrelloList("List2", "Tuesday Education Stuff", false));
        List<TrelloBoard> trelloBoards = Arrays.asList(
                new TrelloBoard("Board1", "Work Stuff", trelloList1),
                new TrelloBoard("Board2", "Home", trelloList1),
                new TrelloBoard("Board3", "Education", trelloList2));

        //When
        List<TrelloBoardDto> trelloBoardDtos = mapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(3, trelloBoardDtos.size());
        assertEquals("Board1", trelloBoardDtos.get(0).getId());
        assertTrue(trelloBoardDtos.get(0).getName().equalsIgnoreCase("WORK STUFF"));
        assertEquals(0, trelloBoardDtos.get(0).getLists().size());
        assertEquals(2, trelloBoardDtos.get(2).getLists().size());
    }

    @Test
    public void mapToList() {
        //Given
        List<TrelloListDto> trelloListDto = Arrays.asList(
                new TrelloListDto("List1", "Monday Education Stuff", false),
                new TrelloListDto("List2", "Tuesday Education Stuff", false),
                new TrelloListDto("List3", "Wednesday Education Stuff", true));

        //When
        List<TrelloList> trelloList = mapper.mapToList(trelloListDto);
        //Then
        assertEquals(3, trelloList.size());
        assertEquals("List1", trelloList.get(0).getId());
        assertFalse(trelloList.get(1).isClosed());
        assertEquals("Wednesday Education Stuff", trelloList.get(2).getName());
        assertTrue(trelloList.get(2).isClosed());
    }

    @Test
    public void mapToListDto() {
        //Given
        List<TrelloList> trelloList = Arrays.asList(
                new TrelloList("List1", "Monday Education Stuff", false),
                new TrelloList("List2", "Tuesday Education Stuff", false),
                new TrelloList("List3", "Wednesday Education Stuff", true),
                new TrelloList("List4", "Friday Education Stuff", true));
        //When
        List<TrelloListDto> trelloListDto = mapper.mapToListDto(trelloList);
        //Then
        assertEquals(4, trelloListDto.size());
        assertEquals("List3", trelloListDto.get(2).getId());
        assertFalse(trelloListDto.get(0).isClosed());
        assertEquals("Tuesday Education Stuff", trelloListDto.get(1).getName());
        assertFalse(trelloListDto.get(1).isClosed());
    }



    @Test
    public void mapToCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Integration", "Dinner", "top", "List1");
        //When
        TrelloCardDto trelloCardDto = mapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Integration", trelloCardDto.getName());
        assertEquals("Dinner", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("List1", trelloCardDto.getListId());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Fun", "Stuff games", "3rd", "List3");
        //When
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Fun", trelloCard.getName());
        assertEquals("Stuff games", trelloCard.getDescription());
        assertEquals("3rd", trelloCard.getPos());
        assertEquals("List3", trelloCard.getListId());
    }

}