/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getbanks.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author marekrigan
 */
public class ComplexMessageDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private LoanRequestDTO dto;
    private List<String> banks;
    
    public ComplexMessageDTO()
    {    
    }
    
    public ComplexMessageDTO(LoanRequestDTO dto, List<String> banks)
    {
        this.dto = dto;
        this.banks = banks;
    }

    public LoanRequestDTO getDto() {
        return dto;
    }

    public void setDto(LoanRequestDTO dto) {
        this.dto = dto;
    }

    public List<String> getBanks() {
        return banks;
    }

    public void setBanks(List<String> banks) {
        this.banks = banks;
    }

    @Override
    public String toString() {
        return "ComplexMessageDTO{" + "dto=" + dto + ", banks=" + banks + '}';
    }
    
    
}
