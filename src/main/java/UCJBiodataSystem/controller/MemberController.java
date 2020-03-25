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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/data")
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    ServletContext context;



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
        return "redirect:/data/all";
    }

    @RequestMapping(path = "/createMember", method = RequestMethod.POST)
    public String createOrUpdateEmployee(Member member)
    {
        memberService.createOrUpdateMember(member);
        return "redirect:/data/all";
    }

    @RequestMapping(value = "/createPdf", method = RequestMethod.GET)
    public void createPdf(HttpServletRequest request, HttpServletResponse response) {
        List<Member> memberList = memberService.getAllMembers();
        boolean isFlag = memberService.createPdf(memberList, context, request, request);

        if (isFlag){
            String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"members"+".pdf");
            filedownload(fullPath, response, "UCJMembers.pdf");
        }
    }

    @RequestMapping(value = "/createExcel", method = RequestMethod.GET)
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Member> memberList = memberService.getAllMembers();
        boolean isFlag = memberService.createExcel(memberList, context, request, request);

        if (isFlag){
            String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"UCJMembers"+".xls");
            filedownload(fullPath, response, "UCJMembers.xls");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()){
            try{
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType= context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename="+fileName);
                OutputStream outputStream = response.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();
                file.delete();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
