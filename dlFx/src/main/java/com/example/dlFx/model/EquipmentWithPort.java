package com.example.dlFx.model;

import com.example.dlFx.dto.MainPageDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentWithPort {
    private Long id;
    private int port;
    private String type;
    private String company;
    private String model;
    private String equipmentTrafficLoad;
    private String equipmentPowerLoad;
    private String equipmentIp;
    private String equipmentMac;
    private String comment;

    // когда этот объект не имеет информации о связи switch-equipment,
    // а только для комментария к порту
    // очищаем все поля, кроме коммента
    public void clean() {
        if (id == -1) {
            equipmentPowerLoad = "";
            equipmentTrafficLoad = "";
        }
    }
}
