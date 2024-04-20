package jobs4u.base.jobOpeningsManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;
import jakarta.persistence.*;
import jobs4u.base.clientManagement.domain.Client;
import jobs4u.base.jobOpeningsManagement.utils.ContractType;
import jobs4u.base.jobOpeningsManagement.utils.JobReference;
import jobs4u.base.jobOpeningsManagement.utils.NrVacancy;
import jobs4u.base.jobOpeningsManagement.utils.WorkingMode;

import jobs4u.base.utils.ClientCode;
import jobs4u.base.utils.PostalAddress;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class JobOpening implements AggregateRoot<JobReference> {

    @Version
    private Long version;

    @EmbeddedId
    private JobReference jobReference;

    private WorkingMode workingMode;
    private NrVacancy nrVacancy;
    private PostalAddress address;
    @AttributeOverrides({
            @AttributeOverride(name="name",
                    column=@Column(name="description",
                            insertable=false, updatable=false))
    })
    private Designation description;
    @AttributeOverrides({
            @AttributeOverride(name="name",
                    column=@Column(name="description",
                            insertable=false, updatable=false))
    })
    private Designation function;
    private ContractType contractType;


    public JobOpening(JobReference jobReference, WorkingMode workingMode, String nrVacancy, String address, String  description, String function, ContractType contractType) {

        this.jobReference = jobReference;
        this.workingMode = workingMode;
        this.nrVacancy = NrVacancy.valueOf(nrVacancy);
        this.address = PostalAddress.valueOf(address);
        this.description = Designation.valueOf(description);
        this.function = Designation.valueOf(function);
        this.contractType = contractType;
    }

    protected JobOpening() {
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public JobReference identity() {
        return jobReference;
    }

}
