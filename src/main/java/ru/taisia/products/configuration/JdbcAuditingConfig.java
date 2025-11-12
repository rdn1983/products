package ru.taisia.products.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.taisia.products.model.Product;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Конфигурация для включения аудита Spring Data JDBC.
 * Автоматически заполняет поля @CreatedBy, @CreatedDate, @LastModifiedBy, @LastModifiedDate.
 */
@Configuration
@EnableJdbcAuditing(auditorAwareRef = "auditorProvider")
public class JdbcAuditingConfig {

    /**
     * Предоставляет информацию о текущем пользователе для аудита.
     * Возвращает имя пользователя из SecurityContext.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    /**
     * Callback для обработки аудита перед сохранением Product.
     * Заполняет поля createdBy, creationDate, modifiedBy, modificationDate.
     */
    @Bean
    public BeforeSaveCallback<Product> productBeforeSaveCallback() {
        return (product, aggregateChange) -> {
            String currentAuditor = getCurrentAuditor();
            LocalDateTime now = LocalDateTime.now();

            if (product.isNew()) {
                // Для новых сущностей заполняем createdBy и creationDate
                if (product.getCreatedBy() == null) {
                    product.setCreatedBy(currentAuditor);
                }
                if (product.getCreationDate() == null) {
                    product.setCreationDate(now);
                }
            }

            // Для всех сущностей (новых и существующих) обновляем modifiedBy и modificationDate
            product.setModifiedBy(currentAuditor);
            product.setModificationDate(now);

            return product;
        };
    }

    /**
     * Получает текущего пользователя из SecurityContext.
     */
    private String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return "system";
        }
        
        String username = authentication.getName();
        
        if ("anonymousUser".equals(username)) {
            return "system";
        }
        
        return username;
    }

    /**
     * Реализация AuditorAware для получения текущего пользователя из SecurityContext.
     */
    private static class SpringSecurityAuditorAware implements AuditorAware<String> {
        
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("system");
            }
            
            String username = authentication.getName();
            
            if ("anonymousUser".equals(username)) {
                return Optional.of("system");
            }
            
            return Optional.of(username);
        }
    }
}
