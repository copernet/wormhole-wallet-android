package whc.com.whc_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateOrImportActivity extends BaseActivity {
    private Button createBtn;
    private Button importBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_import);
        createBtn = findViewById(R.id.createBtn);
        importBtn = findViewById(R.id.importBtn);
        setTilte(getString(R.string.create_or_import_wallet));
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateOrImportActivity.this, PassWordActivityT.class);
                startActivity(it);
            }
        });
        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CreateOrImportActivity.this, ImportWalletActivity.class);
                startActivity(it);
            }
        });
    }


}
