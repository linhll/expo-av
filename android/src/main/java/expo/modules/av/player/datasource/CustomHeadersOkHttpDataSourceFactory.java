package expo.modules.av.player.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;

import java.util.Map;
import java.util.HashMap;

import okhttp3.CacheControl;
import okhttp3.Call;

// Mainly a copy of com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory,
// because it's declared as final :(
public class CustomHeadersOkHttpDataSourceFactory extends HttpDataSource.BaseFactory {
  @NonNull
  private final Call.Factory mCallFactory;
  @Nullable
  private final String mUserAgent;
  @Nullable
  private final CacheControl mCacheControl;

  public CustomHeadersOkHttpDataSourceFactory(@NonNull Call.Factory callFactory, @Nullable String userAgent, @Nullable Map<String, Object> requestHeaders) {
    super();
    mCallFactory = callFactory;
    mUserAgent = userAgent;
    mCacheControl = null;
    updateRequestProperties(requestHeaders);
  }

  protected void updateRequestProperties(@Nullable Map<String, Object> requestHeaders) {
    if (requestHeaders != null) {
      Map<String, String> headers = new HashMap<>();
      for (Map.Entry<String, Object> entry : requestHeaders.entrySet()) {
        Object value = entry.getValue();
        if (value instanceof String) {
          headers.put(entry.getKey(), entry.getValue().toString());
        }
      }
      setDefaultRequestProperties(headers);
    }
  }

  protected OkHttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties defaultRequestProperties) {
    return new OkHttpDataSource(mCallFactory, mUserAgent, mCacheControl, defaultRequestProperties);
  }
}
