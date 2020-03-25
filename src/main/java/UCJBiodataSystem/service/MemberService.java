package UCJBiodataSystem.service;

import UCJBiodataSystem.exception.RecordNotFoundException;
import UCJBiodataSystem.model.Member;
import UCJBiodataSystem.repository.MemberRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAllMembers(){

        List<Member> result = (List<Member>) memberRepository.findAll();

        if (result.size() > 0){
            return result;
        }else {
            return new ArrayList<Member>();
        }
    }

    public Member getMemberById(Long id) throws RecordNotFoundException {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()){
            return member.get();
        }else {
            throw new RecordNotFoundException("No member record exist for the given ID");
        }

    }

    public Member createOrUpdateMember(Member entity)
    {
        if(entity.getId()  == null)
        {
            entity = memberRepository.save(entity);

            return entity;
        }
        else
        {
            Optional<Member> member = memberRepository.findById(entity.getId());

            if(member.isPresent())
            {
                Member newEntity = member.get();
                newEntity.setEmail(entity.getEmail());
                newEntity.setPhoneNumber(entity.getPhoneNumber());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());
                newEntity.setDepartment(entity.getDepartment());
                newEntity.setLpo(entity.getLpo());
                newEntity.setHallOfRecidence(entity.getHallOfRecidence());
                newEntity.setYearOfInduction(entity.getYearOfInduction());
                newEntity.setGender(entity.getGender());
                newEntity.setDateOfBirth(entity.getDateOfBirth());

                newEntity = memberRepository.save(newEntity);

                return newEntity;
            } else {
                entity = memberRepository.save(entity);

                return entity;
            }
        }
    }

    public void deleteMemberById(Long id) throws RecordNotFoundException
    {
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent())
        {
            memberRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }


    public boolean createPdf(List<Member> members, ServletContext context, HttpServletRequest request, HttpServletRequest request1) {

        Document document = new Document(PageSize.A4,15,15,45,30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();

            if (!exists){
                new File(filePath).mkdirs();
            }

            OutputStream os;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+ "/" + "members" + ".pdf"));

            document.open();

            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("All Members", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f,2f, 2f, 2f, 2f, 2f,2f};
            table.setWidths(columnWidths);

            PdfPCell firstName = new PdfPCell(new Paragraph("First Name", tableHeader));
            firstName.setBorderColor(BaseColor.BLACK);
            firstName.setPaddingLeft(10);
            firstName.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstName.setVerticalAlignment(Element.ALIGN_CENTER);
            firstName.setBackgroundColor(BaseColor.GRAY);
            firstName.setExtraParagraphSpace(5f);
            table.addCell(firstName);

            PdfPCell lastName = new PdfPCell(new Paragraph("Last Name", tableHeader));
            lastName.setBorderColor(BaseColor.BLACK);
            lastName.setPaddingLeft(10);
            lastName.setHorizontalAlignment(Element.ALIGN_CENTER);
            lastName.setVerticalAlignment(Element.ALIGN_CENTER);
            lastName.setBackgroundColor(BaseColor.GRAY);
            lastName.setExtraParagraphSpace(5f);
            table.addCell(lastName);

            PdfPCell dateOfBirth = new PdfPCell(new Paragraph("Date of Birth", tableHeader));
            dateOfBirth.setBorderColor(BaseColor.BLACK);
            dateOfBirth.setPaddingLeft(10);
            dateOfBirth.setHorizontalAlignment(Element.ALIGN_CENTER);
            dateOfBirth.setVerticalAlignment(Element.ALIGN_CENTER);
            dateOfBirth.setBackgroundColor(BaseColor.GRAY);
            dateOfBirth.setExtraParagraphSpace(5f);
            table.addCell(dateOfBirth);

            PdfPCell email = new PdfPCell(new Paragraph("Email", tableHeader));
            email.setBorderColor(BaseColor.BLACK);
            email.setPaddingLeft(10);
            email.setHorizontalAlignment(Element.ALIGN_CENTER);
            email.setVerticalAlignment(Element.ALIGN_CENTER);
            email.setBackgroundColor(BaseColor.GRAY);
            email.setExtraParagraphSpace(5f);
            table.addCell(email);

            PdfPCell gender = new PdfPCell(new Paragraph("Gender", tableHeader));
            gender.setBorderColor(BaseColor.BLACK);
            gender.setPaddingLeft(10);
            gender.setHorizontalAlignment(Element.ALIGN_CENTER);
            gender.setVerticalAlignment(Element.ALIGN_CENTER);
            gender.setBackgroundColor(BaseColor.GRAY);
            gender.setExtraParagraphSpace(5f);
            table.addCell(gender);

            PdfPCell phoneNumber = new PdfPCell(new Paragraph("Phone Number", tableHeader));
            phoneNumber.setBorderColor(BaseColor.BLACK);
            phoneNumber.setPaddingLeft(10);
            phoneNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
            phoneNumber.setVerticalAlignment(Element.ALIGN_CENTER);
            phoneNumber.setBackgroundColor(BaseColor.GRAY);
            phoneNumber.setExtraParagraphSpace(5f);
            table.addCell(phoneNumber);

            PdfPCell department = new PdfPCell(new Paragraph("Department", tableHeader));
            department.setBorderColor(BaseColor.BLACK);
            department.setPaddingLeft(10);
            department.setHorizontalAlignment(Element.ALIGN_CENTER);
            department.setVerticalAlignment(Element.ALIGN_CENTER);
            department.setBackgroundColor(BaseColor.GRAY);
            department.setExtraParagraphSpace(5f);
            table.addCell(department);

            PdfPCell hallOfRecidence = new PdfPCell(new Paragraph("Hall of Residence", tableHeader));
            hallOfRecidence.setBorderColor(BaseColor.BLACK);
            hallOfRecidence.setPaddingLeft(10);
            hallOfRecidence.setHorizontalAlignment(Element.ALIGN_CENTER);
            hallOfRecidence.setVerticalAlignment(Element.ALIGN_CENTER);
            hallOfRecidence.setBackgroundColor(BaseColor.GRAY);
            hallOfRecidence.setExtraParagraphSpace(5f);
            table.addCell(hallOfRecidence);

            PdfPCell lpo = new PdfPCell(new Paragraph("LPO", tableHeader));
            lpo.setBorderColor(BaseColor.BLACK);
            lpo.setPaddingLeft(10);
            lpo.setHorizontalAlignment(Element.ALIGN_CENTER);
            lpo.setVerticalAlignment(Element.ALIGN_CENTER);
            lpo.setBackgroundColor(BaseColor.GRAY);
            lpo.setExtraParagraphSpace(5f);
            table.addCell(lpo);

            PdfPCell yearOfInduction = new PdfPCell(new Paragraph("Year of Induction", tableHeader));
            yearOfInduction.setBorderColor(BaseColor.BLACK);
            yearOfInduction.setPaddingLeft(10);
            yearOfInduction.setHorizontalAlignment(Element.ALIGN_CENTER);
            yearOfInduction.setVerticalAlignment(Element.ALIGN_CENTER);
            yearOfInduction.setBackgroundColor(BaseColor.GRAY);
            yearOfInduction.setExtraParagraphSpace(5f);
            table.addCell(yearOfInduction);

            for (Member member : members){
                PdfPCell firstNameValue = new PdfPCell(new Paragraph(member.getFirstName(), tableBody));
                firstNameValue.setBorderColor(BaseColor.BLACK);
                firstNameValue.setPaddingLeft(10);
                firstNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                firstNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
                firstNameValue.setBackgroundColor(BaseColor.WHITE);
                firstName.setExtraParagraphSpace(5f);
                table.addCell(firstNameValue);

                PdfPCell lastNameValue = new PdfPCell(new Paragraph(member.getLastName(), tableBody));
                lastNameValue.setBorderColor(BaseColor.BLACK);
                lastNameValue.setPaddingLeft(10);
                lastNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                lastNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
                lastNameValue.setBackgroundColor(BaseColor.WHITE);
                lastNameValue.setExtraParagraphSpace(5f);
                table.addCell(lastNameValue);

                PdfPCell dateOfBirthValue = new PdfPCell(new Paragraph(member.getDateOfBirth(), tableBody));
                dateOfBirthValue.setBorderColor(BaseColor.BLACK);
                dateOfBirthValue.setPaddingLeft(10);
                dateOfBirthValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                dateOfBirthValue.setVerticalAlignment(Element.ALIGN_CENTER);
                dateOfBirthValue.setBackgroundColor(BaseColor.WHITE);
                dateOfBirthValue.setExtraParagraphSpace(5f);
                table.addCell(dateOfBirthValue);

                PdfPCell emailValue = new PdfPCell(new Paragraph(member.getEmail(), tableBody));
                emailValue.setBorderColor(BaseColor.BLACK);
                emailValue.setPaddingLeft(10);
                emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
                emailValue.setBackgroundColor(BaseColor.WHITE);
                emailValue.setExtraParagraphSpace(5f);
                table.addCell(emailValue);

                PdfPCell genderValue = new PdfPCell(new Paragraph(member.getGender(), tableBody));
                genderValue.setBorderColor(BaseColor.BLACK);
                genderValue.setPaddingLeft(10);
                genderValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                genderValue.setVerticalAlignment(Element.ALIGN_CENTER);
                genderValue.setBackgroundColor(BaseColor.WHITE);
                genderValue.setExtraParagraphSpace(5f);
                table.addCell(genderValue);

                PdfPCell phoneNumberValue = new PdfPCell(new Paragraph(member.getPhoneNumber(), tableBody));
                phoneNumberValue.setBorderColor(BaseColor.BLACK);
                phoneNumberValue.setPaddingLeft(10);
                phoneNumberValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                phoneNumberValue.setVerticalAlignment(Element.ALIGN_CENTER);
                phoneNumberValue.setBackgroundColor(BaseColor.WHITE);
                phoneNumberValue.setExtraParagraphSpace(5f);
                table.addCell(phoneNumberValue);

                PdfPCell departmentValue = new PdfPCell(new Paragraph(member.getDepartment(), tableBody));
                departmentValue.setBorderColor(BaseColor.BLACK);
                departmentValue.setPaddingLeft(10);
                departmentValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                departmentValue.setVerticalAlignment(Element.ALIGN_CENTER);
                departmentValue.setBackgroundColor(BaseColor.WHITE);
                departmentValue.setExtraParagraphSpace(5f);
                table.addCell(departmentValue);

                PdfPCell hallOfResidenceValue = new PdfPCell(new Paragraph(member.getHallOfRecidence(), tableBody));
                hallOfResidenceValue.setBorderColor(BaseColor.BLACK);
                hallOfResidenceValue.setPaddingLeft(10);
                hallOfResidenceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                hallOfResidenceValue.setVerticalAlignment(Element.ALIGN_CENTER);
                hallOfResidenceValue.setBackgroundColor(BaseColor.WHITE);
                hallOfResidenceValue.setExtraParagraphSpace(5f);
                table.addCell(hallOfResidenceValue);

                PdfPCell lpoValue = new PdfPCell(new Paragraph(member.getLpo(), tableBody));
                lpoValue.setBorderColor(BaseColor.BLACK);
                lpoValue.setPaddingLeft(10);
                lpoValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                lpoValue.setVerticalAlignment(Element.ALIGN_CENTER);
                lpoValue.setBackgroundColor(BaseColor.WHITE);
                lpoValue.setExtraParagraphSpace(5f);
                table.addCell(lpoValue);

                PdfPCell yearOfInductionValue = new PdfPCell(new Paragraph(member.getYearOfInduction(), tableBody));
                yearOfInductionValue.setBorderColor(BaseColor.BLACK);
                yearOfInductionValue.setPaddingLeft(10);
                yearOfInductionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                yearOfInductionValue.setVerticalAlignment(Element.ALIGN_CENTER);
                yearOfInductionValue.setBackgroundColor(BaseColor.WHITE);
                yearOfInductionValue.setExtraParagraphSpace(5f);
                table.addCell(yearOfInductionValue);

            }

            document.add(table);
            document.close();
            writer.close();


        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean createExcel(List<Member> memberList, ServletContext context, HttpServletRequest request, HttpServletRequest request1) {

        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists){
            new File(filePath).mkdirs();
        }
        try{
            FileOutputStream outputStream = new FileOutputStream(file+"/"+"UCJMembers"+".xls");
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet worksheet = hssfWorkbook.createSheet("UCJ Members");
            worksheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = hssfWorkbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = worksheet.createRow(0);

            HSSFCell firstName = headerRow.createCell(0);
            firstName.setCellValue("First Name");
            firstName.setCellStyle(headerCellStyle);

            HSSFCell lastName = headerRow.createCell(1);
            lastName.setCellValue("Last Name");
            lastName.setCellStyle(headerCellStyle);

            HSSFCell dateOfBirth = headerRow.createCell(2);
            dateOfBirth.setCellValue("Date of Birth");
            dateOfBirth.setCellStyle(headerCellStyle);

            HSSFCell email = headerRow.createCell(3);
            email.setCellValue("Email");
            email.setCellStyle(headerCellStyle);

            HSSFCell gender = headerRow.createCell(4);
            gender.setCellValue("Gender");
            gender.setCellStyle(headerCellStyle);

            HSSFCell phoneNumber = headerRow.createCell(5);
            phoneNumber.setCellValue("Phone Number");
            phoneNumber.setCellStyle(headerCellStyle);

            HSSFCell department = headerRow.createCell(6);
            department.setCellValue("Department");
            department.setCellStyle(headerCellStyle);

            HSSFCell hallOfResidence = headerRow.createCell(7);
            hallOfResidence.setCellValue("Hall of Residence");
            hallOfResidence.setCellStyle(headerCellStyle);

            HSSFCell lpo = headerRow.createCell(8);
            lpo.setCellValue("LPO");
            lpo.setCellStyle(headerCellStyle);

            HSSFCell yearOfInducion = headerRow.createCell(9);
            yearOfInducion.setCellValue("Year of Induction");
            yearOfInducion.setCellStyle(headerCellStyle);

            int i = 1;
            for (Member member: memberList){
                HSSFRow bodyRow = worksheet.createRow(i);

                HSSFCellStyle bodyCellStyle = hssfWorkbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell firstNameValue = bodyRow.createCell(0);
                firstNameValue.setCellValue(member.getFirstName());
                firstNameValue.setCellStyle(bodyCellStyle);

                HSSFCell lastNameValue = bodyRow.createCell(1);
                lastNameValue.setCellValue(member.getLastName());
                lastNameValue.setCellStyle(bodyCellStyle);

                HSSFCell dateOfBirthValue = bodyRow.createCell(2);
                dateOfBirthValue.setCellValue(member.getDateOfBirth());
                dateOfBirthValue.setCellStyle(bodyCellStyle);

                HSSFCell emailValue = bodyRow.createCell(3);
                emailValue.setCellValue(member.getEmail());
                emailValue.setCellStyle(bodyCellStyle);

                HSSFCell genderValue = bodyRow.createCell(4);
                genderValue.setCellValue(member.getGender());
                genderValue.setCellStyle(bodyCellStyle);

                HSSFCell phoneNumberValue = bodyRow.createCell(5);
                phoneNumberValue.setCellValue(member.getPhoneNumber());
                phoneNumberValue.setCellStyle(bodyCellStyle);

                HSSFCell departmentValue = bodyRow.createCell(6);
                departmentValue.setCellValue(member.getDepartment());
                departmentValue.setCellStyle(bodyCellStyle);

                HSSFCell hallOfResidenceValue = bodyRow.createCell(7);
                hallOfResidenceValue.setCellValue(member.getHallOfRecidence());
                hallOfResidenceValue.setCellStyle(bodyCellStyle);

                HSSFCell lpoValue = bodyRow.createCell(8);
                lpoValue.setCellValue(member.getLpo());
                lpoValue.setCellStyle(bodyCellStyle);

                HSSFCell yearOfInductionValue = bodyRow.createCell(9);
                yearOfInductionValue.setCellValue(member.getYearOfInduction());
                yearOfInductionValue.setCellStyle(bodyCellStyle);

                i++;

            }

            hssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
