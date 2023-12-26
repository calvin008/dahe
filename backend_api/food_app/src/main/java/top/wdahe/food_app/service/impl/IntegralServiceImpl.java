package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.Integral;
import top.wdahe.entity.User;
import top.wdahe.entity.dto.IntegralDTO;
import top.wdahe.entity.vo.IntegralVO;
import top.wdahe.food_app.mapper.IntegralMapper;
import top.wdahe.food_app.mapper.UserMapper;
import top.wdahe.food_app.service.IntegralService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IntegralServiceImpl implements IntegralService {

        @Resource
        private IntegralMapper integralMapper;

        @Resource
        private UserMapper userMapper;

        @Override
        @Transactional(rollbackFor = Exception.class)
        public IntegralVO signIn(String wxOpenid) throws ServiceException {
                //查询用户是否签到过
                LambdaQueryWrapper<Integral> queryWrapper = new LambdaQueryWrapper<Integral>().like(Integral::getWxOpenid,wxOpenid);
                Integral signIn = integralMapper.selectOne(queryWrapper);
                //查询user表中的用户
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().like(User::getWxOpenid,wxOpenid);
                User user = userMapper.selectOne(wrapper);

                //实体化Vo
                IntegralVO integralVO = new IntegralVO();

                /*如果没有签到,直接新增*/
                if(null == signIn) {
                        /*添加时间*/
                        integralMapper.insert(new Integral(wxOpenid,1, LocalDateTime.now()));
                        /*添加用户积分*/
                        user.setIntegral(user.getIntegral() + 1);
                        userMapper.update(user,wrapper);

                        //第一次签到,创建数据,diff为空
                        integralVO.setDaysDiff(null);
                } else { //签到过
                        //判断最后签到日期和当前日期是否超过一天
                        LocalDate signInTime = signIn.getUpdateTime().toLocalDate();
                        LocalDate currTime = LocalDate.now();
                        long daysDiff = ChronoUnit.DAYS.between(signInTime,currTime);
                        if(daysDiff <= 0) {
                                integralVO.setDaysDiff(0);
                        }

                        if(daysDiff > 1) {
                                integralVO.setDaysDiff(2);
                                signIn.setContinueDays(1);
                                user.setIntegral(user.getIntegral() + 1);
                        }

                        if(daysDiff == 1) {
                                integralVO.setDaysDiff(1);
                                //没有超过1天把连续签到天数+1
                                signIn.setContinueDays(signIn.getContinueDays() + 1);
                                //连续签到,积分累加
                                if(signIn.getContinueDays() < 8) {
                                        user.setIntegral(user.getIntegral()  + signIn.getContinueDays());
                                } else {
                                        user.setIntegral(user.getIntegral() + 7);
                                }
                        }
                        signIn.setUpdateTime(LocalDateTime.now());
                        integralMapper.update(signIn,queryWrapper);
                        userMapper.update(user,wrapper);

                }

                integralVO.setIntegrals(user.getIntegral());
                integralVO.setContinueDays(signIn.getContinueDays());
                integralVO.setUpdateTime(signIn.getUpdateTime());
                return integralVO;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public List<IntegralDTO> signInList(String wxOpenid) {
                QueryWrapper<Integral> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Integral::getWxOpenid,wxOpenid);
                Integral signIn = integralMapper.selectOne(queryWrapper);
                List<IntegralDTO> list = new ArrayList<>(7);

                //签到过
                Integer continueDays = signIn.getContinueDays();
                if(null == signIn || continueDays == 0){
                        for(int i=1; i<8; i++) {
                                LocalDateTime d = LocalDateTime.now();
                                list.add(new IntegralDTO(d,"+1"));
                        }
                } else {
                        //签到2-7天,积分累加
                        if(continueDays >=1 && continueDays <=6){
                                LocalDateTime localDate = signIn.getUpdateTime();
                                list.add(new IntegralDTO(localDate,"+" + signIn.getContinueDays()));
                                for(int i = 1; i< continueDays; i++) {
                                        Integer num = signIn.getContinueDays() -i;
                                        list.add(new IntegralDTO(localDate.plusDays(-i),"+" + num));
                                }
                        } else {
                                //第七天和七天后的签到积分只加7
                                LocalDateTime localDate = signIn.getUpdateTime();
                                list.add(new IntegralDTO(localDate,"+7"));
                                for(int i = 1; i< continueDays; i++) {
                                    if(i>=7) {
                                            list.add(new IntegralDTO(localDate,"+7"));
                                    } else  {
                                            Integer num = signIn.getContinueDays() -1;
                                            list.add(new IntegralDTO(localDate.plusDays(-i),"+" + num));
                                    }
                                }
                        }
                }
                return list;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public Integer signDay(String wxOpenid) {
                //查找用户
                QueryWrapper<Integral> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Integral::getWxOpenid,wxOpenid);
                Integral signIn = integralMapper.selectOne(queryWrapper);

                //判断签到日期和当前日期是否超过一天
                LocalDate signInTime =  signIn.getUpdateTime().toLocalDate();

                LocalDate currTime = LocalDate.now();
                long dayDiff = ChronoUnit.DAYS.between(signInTime,currTime);

                if(dayDiff > 1) {
                        //超过一天,联系签到天数不返回
                        return null;

                } else  {
                        //没有超过一天,返回连续签到天数
                        return signIn.getContinueDays();
                }

        }
}
