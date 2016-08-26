package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.model.Occurrence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by aciurea on 8/18/2016.
 */
public class DTOService {
    public static OccurrenceDTO toDto(Occurrence occurrence){
        OccurrenceDTO dto=new OccurrenceDTO(occurrence.getWord().getValue(), occurrence.getFrequency());
        return dto;
    }
    public static List<OccurrenceDTO> toDto(Collection<Occurrence> occurrences){
        List<OccurrenceDTO> dtos=new ArrayList<>();
        for(Occurrence occurrence : occurrences) {
            dtos.add(new OccurrenceDTO(occurrence.getWord().getValue(), occurrence.getFrequency()));
        }
        return dtos;
    }



}
