package UCJBiodataSystem.service;

import UCJBiodataSystem.exception.RecordNotFoundException;
import UCJBiodataSystem.model.Member;
import UCJBiodataSystem.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());
                newEntity.setDepartment(entity.getDepartment());
                newEntity.setLpo(entity.getLpo());
                newEntity.setHallOfRecidence(entity.getHallOfRecidence());
                newEntity.setYearOfInduction(entity.getYearOfInduction());

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


}
