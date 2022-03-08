package br.senai.sp.cotia.app_salario_liq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

        private EditText cpSalario, cpDependentes, mostraSalBruto, mostraDescontos, mostraSalLiq;
        private Button btCalcular, btLimpar;
        private RadioGroup rgSaude, rgVT, rgVA, rgVR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpSalario = findViewById(R.id.cpSalario);
        cpDependentes = findViewById(R.id.cpDependentes);
        mostraSalBruto = findViewById(R.id.mostraSalBruto);
        mostraDescontos = findViewById(R.id.mostraDescontos);
        mostraSalLiq = findViewById(R.id.mostraSalLiq);
        btCalcular = findViewById(R.id.btCalcular);
        btLimpar = findViewById(R.id.bt_limpar);
        rgSaude= findViewById(R.id.rgSaude);
        rgVT = findViewById(R.id.rgVT);
        rgVA = findViewById(R.id.rgVA);
        rgVR = findViewById(R.id.rgVR);

        btLimpar.setOnClickListener(v -> {
            cpSalario.setText("");
            cpDependentes.setText("");
            mostraSalBruto.setText("");
            mostraDescontos.setText("");
            mostraSalLiq.setText("");
        });

        btCalcular.setOnClickListener(v -> { //v = view
        if (cpSalario.getText().toString().isEmpty()){
            cpSalario.setError(getString(R.string.valida_salario));
            Toast.makeText(getBaseContext(), R.string.valida_salario, Toast.LENGTH_SHORT).show();
        }else if (cpDependentes.getText().toString().isEmpty()){
            cpDependentes.setError(getString(R.string.valida_dependentes));
            Snackbar.make(cpDependentes, R.string.valida_dependentes, Snackbar.LENGTH_SHORT).show();
        }else{
            double salario = 0, inss = 0, irrf, desct, salLiq, plnSaud = 0, valt = 0, vala = 0, valr = 0;
            int dependentes = 0;
            salario = Double.parseDouble(cpSalario.getText().toString());
            dependentes = Integer.parseInt(cpDependentes.getText().toString());
            switch (rgSaude.getCheckedRadioButtonId()){
                case R.id.rdStandart:
                    if (salario <= 3000){
                        plnSaud = 60;
                    }else if (salario > 3000){
                        plnSaud = 80;
                    }
                    break;
                case R.id.rdBasico:
                    if (salario <= 3000){
                        plnSaud = 80;
                    }else if (salario > 3000){
                        plnSaud = 110;
                    }
                    break;
                case R.id.rdSuper:
                    if (salario <= 3000){
                        plnSaud = 95;
                    }else if (salario > 3000){
                        plnSaud = 135;
                    }
                    break;
                case R.id.rdMaster:
                    if (salario <= 3000){
                        plnSaud = 130;
                    }else if (salario > 3000){
                        plnSaud = 180;
                    }
                    break;
            }
            switch (rgVT.getCheckedRadioButtonId()){
                case R.id.simVT:
                       valt = salario * 0.06;
                       break;
                case R.id.naoVT:
                       valt = 0;
                       break;
            }
            switch (rgVR.getCheckedRadioButtonId()){
                case R.id.simVR:
                    if (salario <= 3000){
                        valr = 2.60 * 22;
                    }else if (salario <= 5000){
                        valr = 3.65 * 22;
                    }else{
                        valr = 6.50 * 22;
                    }
                    break;
                case R.id.naoVR:
                    valr = 0;
                    break;
            }
            switch (rgVA.getCheckedRadioButtonId()){
                case R.id.simVA:
                    if (salario <= 3000){
                        vala = 15;
                    }else if (salario <= 5000){
                        vala = 25;
                    }else{
                        vala = 35;
                    }
                    break;
                case R.id.naoVA:
                    vala = 0;
                    break;
            }

            if (salario <= 1212){
                inss = salario * 0.075;
            } else if (salario <= 2427.35){
                inss = salario * 0.09;
                inss = inss - 18.18;
            } else if (salario <= 3641.03){
                inss = salario * 0.12;
                inss = inss - 91.01;
            } else if (salario <= 7087.22){
                inss = salario * 0.14;
                inss = inss - 163.00;
            } else {
                inss = 829.21;
            }

            irrf = salario - inss - (189.59 * dependentes);
            if (irrf <= 1903.98) {
                irrf = 0;
            } else if (irrf <= 2826.65) {
                irrf = irrf * 0.075;
                irrf -= 142.80;
            } else if (irrf <= 3751.05) {
                irrf = irrf * 0.15;
                irrf -= 354.80;
            } else if (irrf <= 4664.68) {
                irrf = irrf * 0.225;
                irrf -= 636.13;
            } else {
                irrf = irrf * 0.275;
                irrf -= 869.36;
            }

            salLiq = salario - inss - plnSaud - valt - vala - valr - irrf;
            desct = (1 - salLiq / salario) * 100;

            mostraSalBruto.setText("R$ " + salario);
            mostraDescontos.setText("% " + desct + "");
            mostraSalLiq.setText("R$ " + salLiq);
        }
        });
    }
}