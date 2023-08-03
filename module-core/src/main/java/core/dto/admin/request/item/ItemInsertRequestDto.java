package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemStep;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ItemInsertRequestDto {
    @NotNull
    private ItemStep step;
    @NotNull
    private Long companyId;
    @NotNull
    private Long brandId;
    private ItemInsertStep1RequestDto step1Info;
    private ItemInsertStep2RequestDto step2Info;
    private ItemInsertStep3RequestDto step3Info;

    public boolean isAvailableDataByStep() {
        if (this.step == null) {
            return false;
        }

        switch (this.step) {
            case STEP_ONE:
                return step1Info != null && step1Info.isAvailableData();
            case STEP_TWO:
                return step2Info != null && step2Info.isAvailableData();
            case STEP_THREE:
                return step3Info != null && step3Info.isAvailableData();
            default:
                return false;
        }
    }
}
