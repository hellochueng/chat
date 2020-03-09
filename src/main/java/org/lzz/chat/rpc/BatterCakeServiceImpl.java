package org.lzz.chat.rpc;

public class BatterCakeServiceImpl implements BatterCakeService {

    @Override
    public String sellBatterCake(String name) {
        // TODO Auto-generated method stub
        if("1".equals(name))
            throw new RuntimeException("321");
        return name+"煎饼,卖的特别好";
    }
}