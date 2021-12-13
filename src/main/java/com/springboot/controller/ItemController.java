package com.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.User;
import com.springboot.util.ItemHelper;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Value(value = "${myfile}")
    private String myfile;
    @Value(value = "${mypath}")
    private String mypath;

    @Resource
    private ItemHelper itemHelper;
    @Resource
    private ManholeBiz manholeBiz;
    @Resource
    private ItemBiz itemBiz;
    private Map<String, Object> map = null;

    @RequestMapping(value = "/editinfo")
    public ModelAndView editInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        User user = (User) AppUtils.findMap("user");
        Manhole manhole = manholeBiz.findInfoManhole(id, user);
        if (StringUtils.isEmpty(manhole))
            return view;
        List<Item> items = itemBiz.findListItem(manhole);
        if (items == null || items.size() == 0)
            items = itemBiz.appendItem(manhole);
        view.setViewName("iteminfo/editinfo");
        view.addObject("manhole", manhole);
        view.addObject("items", items);
        view.addObject("path", mypath);
        return view;
    }

    @RequestMapping(value = "/findinfo")
    public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("userview/failure");
        Manhole manhole = manholeBiz.findInfoManhole(id, null);
        if (StringUtils.isEmpty(manhole))
            return view;
        List<Item> items = itemBiz.findListItem(manhole);
        if (items == null || items.size() == 0)
            items = itemBiz.appendItem(manhole);
        view.setViewName("iteminfo/findinfo");
        view.addObject("manhole", manhole);
        view.addObject("items", items);
        view.addObject("path", mypath);
        return view;
    }

    @RequestMapping(value = "/checkview")
    public ModelAndView chckview(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Manhole manhole = manholeBiz.findInfoManhole(map);
        if (StringUtils.isEmpty(manhole))
            return view;
        List<Item> items = itemBiz.findListItem(manhole);
        if (items == null || items.size() == 0)
            items = itemBiz.appendItem(manhole);
        view.setViewName("iteminfo/checkview");
        view.addObject("manhole", manhole);
        view.addObject("items", items);
        view.addObject("path", mypath);
        return view;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Manhole manhole, MultipartFile[] files) {
        ModelAndView view = new ModelAndView("redirect:/success");
        User user = (User) AppUtils.findMap("user");
        Manhole iManhole = manholeBiz.findInfoManhole(manhole.getId(), user);
        if (StringUtils.isEmpty(iManhole)) {
            view.setViewName("redirect:/failure");
            return view;
        }
        List<String> location = new ArrayList<>(16);
        List<String> internal = new ArrayList<>(16);
        List<String> photo1 = new ArrayList<>(16);
        List<String> photo2 = new ArrayList<>(16);
        List<String> photo3 = new ArrayList<>(16);
        List<String> photo4 = new ArrayList<>(16);
        List<String> photo5 = new ArrayList<>(16);
        List<String> photo6 = new ArrayList<>(16);
        for (Item item : manhole.getItems()) {
            if ("Manhole Location".equals(item.getExplain1()) && item.getPhoto1() != null)
                location.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("Manhole Location".equals(item.getExplain2()) && item.getPhoto2() != null)
                location.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("Manhole Internal Photo".equals(item.getExplain1()) && item.getPhoto1() != null)
                internal.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("Manhole Internal Photo".equals(item.getExplain2()) && item.getPhoto2() != null)
                internal.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("COVER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo1.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("COVER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo1.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("IRON / LADDER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo2.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("IRON / LADDER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo2.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("SHAFT".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo3.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("SHAFT".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo3.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("CHAMBER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo4.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("CHAMBER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo4.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("BENCHING".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo5.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("BENCHING".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo5.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("OTHERS".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo6.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("OTHERS".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo6.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));
        }
        if (location.size() != 0)
            iManhole.setPhotono1(iManhole.getDrainage() + " - " + String.join(",", location));
        if (internal.size() != 0)
            iManhole.setPhotono2(iManhole.getDrainage() + " - " + String.join(",", internal));
        if (photo1.size() != 0) {
            iManhole.setCover("Y");
            iManhole.setPhoto1(iManhole.getDrainage() + " - " + String.join(",", photo1));
        }
        if (photo2.size() != 0) {
            iManhole.setIron("Y");
            iManhole.setPhoto2(iManhole.getDrainage() + " - " + String.join(",", photo2));
        }
        if (photo3.size() != 0) {
            iManhole.setShaft("Y");
            iManhole.setPhoto3(iManhole.getDrainage() + " - " + String.join(",", photo3));
        }
        if (photo4.size() != 0) {
            iManhole.setChambers("Y");
            iManhole.setPhoto4(iManhole.getDrainage() + " - " + String.join(",", photo4));
        }
        if (photo5.size() != 0) {
            iManhole.setBenching("Y");
            iManhole.setPhoto5(iManhole.getDrainage() + " - " + String.join(",", photo5));
        }
        if (photo6.size() != 0) {
            iManhole.setOther("Y");
            iManhole.setPhoto6(iManhole.getDrainage() + " - " + String.join(",", photo6));
        }
        manholeBiz.updateManhole(iManhole);
        itemBiz.replacItem(manhole, files);
        return view;
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ModelAndView commit(Manhole manhole, MultipartFile[] files) {
        ModelAndView view = new ModelAndView("redirect:/success");
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", manhole.getId(), "company", user.getCompany());
        Manhole iManhole = manholeBiz.findInfoManhole(map);
        if (StringUtils.isEmpty(iManhole)) {
            view.setViewName("redirect:/failure");
            return view;
        }
        List<String> location = new ArrayList<>(16);
        List<String> internal = new ArrayList<>(16);
        List<String> photo1 = new ArrayList<>(16);
        List<String> photo2 = new ArrayList<>(16);
        List<String> photo3 = new ArrayList<>(16);
        List<String> photo4 = new ArrayList<>(16);
        List<String> photo5 = new ArrayList<>(16);
        List<String> photo6 = new ArrayList<>(16);
        for (Item item : manhole.getItems()) {
            if ("Manhole Location".equals(item.getExplain1()) && item.getPhoto1() != null)
                location.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("Manhole Location".equals(item.getExplain2()) && item.getPhoto2() != null)
                location.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("Manhole Internal Photo".equals(item.getExplain1()) && item.getPhoto1() != null)
                internal.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("Manhole Internal Photo".equals(item.getExplain2()) && item.getPhoto2() != null)
                internal.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("COVER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo1.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("COVER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo1.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("IRON / LADDER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo2.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("IRON / LADDER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo2.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("SHAFT".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo3.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("SHAFT".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo3.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("CHAMBER".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo4.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("CHAMBER".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo4.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("BENCHING".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo5.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("BENCHING".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo5.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));

            if ("OTHERS".equals(item.getExplain1()) && item.getPhoto1() != null)
                photo6.add(item.getPhoto1().substring(item.getPhoto1().length() - 3));
            if ("OTHERS".equals(item.getExplain2()) && item.getPhoto2() != null)
                photo6.add(item.getPhoto2().substring(item.getPhoto2().length() - 3));
        }
        if (location.size() != 0)
            iManhole.setPhotono1(iManhole.getDrainage() + " - " + String.join(",", location));
        if (internal.size() != 0)
            iManhole.setPhotono2(iManhole.getDrainage() + " - " + String.join(",", internal));
        if (photo1.size() != 0) {
            iManhole.setCover("Y");
            iManhole.setPhoto1(iManhole.getDrainage() + " - " + String.join(",", photo1));
        }
        if (photo2.size() != 0) {
            iManhole.setIron("Y");
            iManhole.setPhoto2(iManhole.getDrainage() + " - " + String.join(",", photo2));
        }
        if (photo3.size() != 0) {
            iManhole.setShaft("Y");
            iManhole.setPhoto3(iManhole.getDrainage() + " - " + String.join(",", photo3));
        }
        if (photo4.size() != 0) {
            iManhole.setChambers("Y");
            iManhole.setPhoto4(iManhole.getDrainage() + " - " + String.join(",", photo4));
        }
        if (photo5.size() != 0) {
            iManhole.setBenching("Y");
            iManhole.setPhoto5(iManhole.getDrainage() + " - " + String.join(",", photo5));
        }
        if (photo6.size() != 0) {
            iManhole.setOther("Y");
            iManhole.setPhoto6(iManhole.getDrainage() + " - " + String.join(",", photo6));
        }
        manholeBiz.updateManhole(iManhole);
        itemBiz.replacItem(manhole, files);
        return view;
    }

    @RequestMapping(value = "/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        Item item = itemBiz.findInfoItem(id, user);
        if (!StringUtils.isEmpty(item))
            itemBiz.deleteItem(item);
        return true;
    }

    @RequestMapping(value = "/remove")
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.findMap("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Item item = itemBiz.findInfoItem(map);
        if (!StringUtils.isEmpty(item))
            itemBiz.deleteItem(item);
        return true;
    }


    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public ModelAndView imports(int id, MultipartFile[] files) {
        ModelAndView view = new ModelAndView();
        Manhole manhole = manholeBiz.findInfoManhole(id, null);
        if (!StringUtils.isEmpty(manhole))
            itemHelper.ItemMode(manhole, files);
        view.setViewName("redirect:/item/editinfo?id=" + id);
        return view;
    }

}
