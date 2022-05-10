package ddss.storage.api;

import ddss.storage.domain.AvailableMegabytesNumber;
import ddss.storage.domain.DeviceUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
@RequestMapping("/storage/admin")
public class CatalogInteractionController {

    private static final String adminUsername = "admin";

    private static long calcAvailableMegabytes() {
        long availableBytes = 0;

        File[] roots = File.listRoots();
        for (File root : roots) {
            availableBytes += root.getFreeSpace();
        }

        return (long) ((double) availableBytes / 1024.0);
    }

    @GetMapping(value = "/available-megabytes")
    public ResponseEntity<AvailableMegabytesNumber> getAvailableMegabytes(@AuthenticationPrincipal DeviceUser user) {
        if (!user.getUsername().equals(adminUsername))
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(
                new AvailableMegabytesNumber(calcAvailableMegabytes()),
                HttpStatus.OK);
    }
}
