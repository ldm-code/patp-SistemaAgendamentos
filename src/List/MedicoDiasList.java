package List;

import java.util.ArrayList;
import java.util.List;

public class MedicoDiasList {

    private int medicoId;

    // guarda os dias como número (1 a 7)
    private List<Integer> dias = new ArrayList<>();

    public MedicoDiasList() {
    }

    public MedicoDiasList(int medicoId) {
        this.medicoId = medicoId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public List<Integer> getDias() {
        return dias;
    }

    public void setDias(List<Integer> dias) {
        this.dias = dias;
    }

    public void addDia(int dia) {
        if (!dias.contains(dia)) {
            dias.add(dia);
        }
    }

    public void removerDia(int dia) {
        dias.remove(Integer.valueOf(dia));
    }

    public boolean contemDia(int dia) {
        return dias.contains(dia);
    }

    @Override
    public String toString() {
        return "MedicoDiasList{" +
                "medicoId=" + medicoId +
                ", dias=" + dias +
                '}';
    }
}
