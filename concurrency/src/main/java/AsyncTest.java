import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AsyncTest {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://google.com")
                .build();

        class OkHttpResponseFuture implements Callback {
            public final CompletableFuture<Response> future = new CompletableFuture<>();

            public OkHttpResponseFuture() {
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                    future.complete(response);
                }
            }
        }

        OkHttpResponseFuture future = new OkHttpResponseFuture();

        client.newCall(request).enqueue(future);

        AsyncTest m = new AsyncTest();

        //synchronized (m) {
        //    try {
        //        m.wait(1000);
        //    }catch (Exception e) {
        //        System.out.println("Exception");
        //    }
        //}
        while (!future.future.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Exception");
            }
        }

        System.out.println("done");

    }

}
