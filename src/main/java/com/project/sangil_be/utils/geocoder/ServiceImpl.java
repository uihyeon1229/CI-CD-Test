package com.project.sangil_be.utils.geocoder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sangil_be.dto.XYTransferDto;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.repository.Mountain100Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceImpl {
    private final Mountain100Repository mountain100Repository;

    public ServiceImpl(Mountain100Repository mountain100Repository) {
        this.mountain100Repository = mountain100Repository;
    }

    public XYTransferDto getXYMapfromJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> XYMap = new HashMap<String, String>();
        XYTransferDto mountain100Dto = new XYTransferDto();

        try {
            TypeReference<Map<String, Object>> typeRef
                    = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);

            @SuppressWarnings("unchecked")
            List<Map<String, String>> docList
                    = (List<Map<String, String>>) jsonMap.get("documents");

            Map<String, String> adList = (Map<String, String>) docList.get(0);
            XYMap.put("x", adList.get("x"));
            XYMap.put("y", adList.get("y"));
            mountain100Dto.setLng(Double.valueOf(XYMap.get("x")));
            mountain100Dto.setLat(Double.valueOf(XYMap.get("y")));

//            AddressTransfer addressTransfer = new AddressTransfer();
//
//            ServiceImpl service = new ServiceImpl(mountain100Repository);
//
//            for (int i = 0; i < mountain100Repository.count(); i++) {
//                String address = mountain100Repository.findAll().get(i).getMountainAddress();
////                addressTransfer.getKakaoApiFromAddress(address);
//                service.getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(address));
//
//                mountain100Dto.getXyUpdateList().get(i).setLat(Double.valueOf(XYMap.get("x")));
//                mountain100Dto.getXyUpdateList().get(i).setLng(Double.valueOf(XYMap.get("y")));
//
//            }


        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mountain100Dto;
    }

    @Transactional
    public void updateXY() {
        AddressTransfer addressTransfer = new AddressTransfer();
        List<Mountain100> mountain100List = mountain100Repository.findAll();
        for (int i = 0; i < mountain100List.size(); i++) {
            Mountain100 mountain100 = mountain100Repository.findAll().get(i);
            System.out.println(i);
            System.out.println(mountain100.getMountainAddress());

            XYTransferDto mountain100Dto = new XYTransferDto(

                    getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(mountain100.getMountainAddress())).getLat(),
                    getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(mountain100.getMountainAddress())).getLng());
            mountain100.update(mountain100Dto.getLat(),mountain100Dto.getLng());
        }
    }
}
