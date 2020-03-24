package UCJBiodataSystem.controller;

import UCJBiodataSystem.exception.RecordNotFoundException;
import UCJBiodataSystem.model.Member;
import UCJBiodataSystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/data")
public class MemberController {

    @Autowired
    MemberService memberService;



    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String getAllMembers(Model model)
    {
        List<Member> memberList = memberService.getAllMembers();
        model.addAttribute("members", memberList);
        return "list-members";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"}, method = RequestMethod.GET)
    public String editMemberById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            Member entity = memberService.getMemberById(id.get());
            model.addAttribute("member", entity);
        } else {
            model.addAttribute("member", new Member());
        }
        return "add-edit-member";
    }

    @RequestMapping(path = {"/detail", "/detail/{id}"}, method = RequestMethod.GET)
    public String detailMemberById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            Member entity = memberService.getMemberById(id.get());
            model.addAttribute("member", entity);
        } else {
            model.addAttribute("member", new Member());
        }
        return "detail-member";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteMemberById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        memberService.deleteMemberById(id);
        return "redirect:/all";
    }

    @RequestMapping(path = "/createMember", method = RequestMethod.POST)
    public String createOrUpdateEmployee(Member member)
    {
        memberService.createOrUpdateMember(member);
        return "redirect:/data/all";
    }
}
