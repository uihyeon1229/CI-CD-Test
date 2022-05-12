package com.project.sangil_be.utils.geocoder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sangil_be.dto.XYTransferDto;
import com.project.sangil_be.model.Mountain;
import com.project.sangil_be.repository.MountainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceImpl {
    private final MountainRepository mountainRepository;

    public ServiceImpl(MountainRepository mountainRepository) {
        this.mountainRepository = mountainRepository;
    }

    public XYTransferDto getXYMapfromJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> XYMap = new HashMap<String, String>();
        XYTransferDto mountainDto = new XYTransferDto();

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
            mountainDto.setLng(Double.valueOf(XYMap.get("x")));
            mountainDto.setLat(Double.valueOf(XYMap.get("y")));

//            AddressTransfer addressTransfer = new AddressTransfer();
//
//            ServiceImpl service = new ServiceImpl(mountainRepository);
//
//            for (int i = 0; i < mountainRepository.count(); i++) {
//                String address = mountainRepository.findAll().get(i).getMountainAddress();
////                addressTransfer.getKakaoApiFromAddress(address);
//                service.getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(address));
//
//                mountainDto.getXyUpdateList().get(i).setLat(Double.valueOf(XYMap.get("x")));
//                mountainDto.getXyUpdateList().get(i).setLng(Double.valueOf(XYMap.get("y")));
//
//            }


        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mountainDto;
    }

    @Transactional
    public void updateXY() {
        AddressTransfer addressTransfer = new AddressTransfer();
        for (int i = 0; i < 100; i++) {
            Mountain mountain = mountainRepository.findAll().get(i);
            System.out.println(mountain.getMountainAddress());
            XYTransferDto mountainDto = new XYTransferDto(

                    getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(mountain.getMountainAddress())).getLat(),
                    getXYMapfromJson(addressTransfer.getKakaoApiFromAddress(mountain.getMountainAddress())).getLng());
            mountain.update(mountainDto.getLat(),mountainDto.getLng());
        }
    }
}
