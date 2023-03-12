package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.ChartData;

public interface IChartService {

    ChartData getAreaData() throws Exception;

    ChartData getBarData() throws Exception;

}
