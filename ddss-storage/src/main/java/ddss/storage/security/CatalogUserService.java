package ddss.storage.security;

import ddss.storage.data.CatalogUserRepository;
import ddss.storage.domain.CatalogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CatalogUserService implements UserDetailsService {

    @Autowired
    private CatalogUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CatalogUser user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "device user '" + username + "' not found");
        }

        return user;
    }
}
