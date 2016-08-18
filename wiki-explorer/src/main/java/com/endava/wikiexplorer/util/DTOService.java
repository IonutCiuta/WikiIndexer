package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.model.Occurence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by aciurea on 8/18/2016.
 */
public class DTOService {
    public static OccurrenceDTO toDto(Occurence occurence){
        OccurrenceDTO dto=new OccurrenceDTO(occurence.getWord().getValue(),occurence.getFrequency());
        return dto;
    }
    public static List<OccurrenceDTO> toDto(Collection<Occurence> occurences){
        List<OccurrenceDTO> dtos=new ArrayList<>();
        for(Occurence occurence:occurences) {
            dtos.add(new OccurrenceDTO(occurence.getWord().getValue(), occurence.getFrequency()));
        }
        return dtos;
    }



}
