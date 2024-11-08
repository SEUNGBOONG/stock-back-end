package com.example.investment_api.portfolio.infrastructure;

import lombok.Getter;

@Getter
public enum PortfolioType {

    RANGE_2000_3000_AGGRESSIVE("주식의 비중을 높여 성장 가능성을 최대화, 채권과 저축으로 리스크를 완화합니다."),
    RANGE_2000_3000_CONSERVATIVE("안정적 성장을 목표로 주식 비중을 조금 높이며 채권 비중을 강화합니다."),
    RANGE_3000_4000_AGGRESSIVE("주식 비중을 높여 수익성을 극대화하며, 채권과 저축을 통해 손실을 방어할 수 있는 구성을 만듭니다."),
    RANGE_3000_4000_CONSERVATIVE("안정적 성장을 목표로 주식 비중을 조금 높이며 채권 비중을 강화합니다."),
    RANGE_4000_5000_AGGRESSIVE("자산 증식의 기회를 적극적으로 활용하는 주식 비중을 가지며, 안정성을 위한 채권 비중을 줄입니다."),
    RANGE_4000_5000_CONSERVATIVE("안정적 성장을 목표로 주식과 채권에 균형 있게 투자하고, 저축으로 비상자금을 마련합니다."),
    RANGE_5000_6000_AGGRESSIVE("공격적인 자산 증식을 위해 주식 비중을 더욱 높이고, 소량의 채권과 부동산으로 리스크를 분산합니다."),
    RANGE_5000_6000_CONSERVATIVE("안정적 성장을 목표로 주식과 채권에 균형 있게 투자하고, 저축으로 비상자금을 마련합니다."),
    RANGE_6000_7000_AGGRESSIVE("성장 잠재력을 극대화하려고 주식 비중을 크게 늘리며, 일부 자산은 부동산으로 분산합니다."),
    RANGE_6000_7000_CONSERVATIVE("소극적 투자자는 주식, 채권, 저축을 균형 있게 유지하며 안전한 성장을 도모합니다."),
    RANGE_7000_8000_AGGRESSIVE("주식과 부동산 비중을 크게 두고 자산 증식의 기회를 극대화합니다. 저축 비중을 낮춰 유동성은 최소화합니다."),
    RANGE_7000_8000_CONSERVATIVE("주식과 채권에 적절히 투자하면서 안정적인 저축 비중을 유지해 균형 잡힌 성장을 지향합니다."),
    RANGE_8000_9000_AGGRESSIVE("고수익을 목표로 주식 비중을 극대화합니다. 손실을 감수할 수 있는 자산 구조를 통해 장기적 성장을 지향합니다."),
    RANGE_8000_9000_CONSERVATIVE("주식과 채권의 균형을 통해 안정성과 성장을 동시에 추구하며, 저축으로 유동성을 확보합니다."),
    RANGE_9000_10000_AGGRESSIVE("주식 투자 비중을 최대한으로 끌어올려 자산 증식에 집중하며, 부동산으로 약간의 리스크 분산을 시도합니다."),
    RANGE_9000_10000_CONSERVATIVE("안정적인 성장을 위해 주식 비중을 적당히 높이고, 부동산을 포함한 다양한 자산으로 분산 투자합니다."),
    Default("고연봉자, 저연봉자는 아직 데이터가 부족합니다.");

    private final String description;

    PortfolioType(String description) {
        this.description = description;
    }

}
