package emergentes.dataBase;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import clases.Producto;
import dataBase.ProductoDAO;

import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductoDAO_Impl implements ProductoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Producto> __insertionAdapterOfProducto;

  public ProductoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProducto = new EntityInsertionAdapter<Producto>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Producto` (`id`,`nombre`,`precio`,`cantidad`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Producto value) {
        stmt.bindLong(1, value.getReferencia());
        if (value.getNombre() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNombre());
        }
        stmt.bindLong(3, value.getPrecio());
        stmt.bindLong(4, value.getCantidad());
      }
    };
  }

  @Override
  public void insert(final Producto producto) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProducto.insert(producto);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Producto> getAll() {
    final String _sql = "SELECT * FROM producto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfReferencia = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
      final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
      final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
      final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Producto _item;
        final int _tmpReferencia;
        _tmpReferencia = _cursor.getInt(_cursorIndexOfReferencia);
        final String _tmpNombre;
        if (_cursor.isNull(_cursorIndexOfNombre)) {
          _tmpNombre = null;
        } else {
          _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
        }
        final int _tmpPrecio;
        _tmpPrecio = _cursor.getInt(_cursorIndexOfPrecio);
        final int _tmpCantidad;
        _tmpCantidad = _cursor.getInt(_cursorIndexOfCantidad);
        _item = new Producto(_tmpReferencia,_tmpNombre,_tmpPrecio,_tmpCantidad);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
