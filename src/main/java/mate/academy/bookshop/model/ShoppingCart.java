package mate.academy.bookshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE book SET is_deleted = 1 WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "shopping_carts")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ShoppingCart {
    @Id
    private Long id;
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "id")
    private User user;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "shoppingCart")
    private Set<CartItem> cartItems = new HashSet<>();
    @Column(nullable = false)
    private boolean isDeleted;
}
