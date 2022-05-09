package ddss.storage.api;

import ddss.storage.data.DepositRepository;
import ddss.storage.domain.Data;
import ddss.storage.domain.Deposit;
import ddss.storage.domain.DeviceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage")
public class DownloadController {

    @Autowired
    DepositRepository depositRepo;

    @GetMapping(value = "/download", consumes = "application/json")
    public ResponseEntity<Data> download(@AuthenticationPrincipal DeviceUser user) {
        Deposit deposit = depositRepo.findDepositByCatalogRecordId(1);
        Data data = new Data(deposit.getData());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
