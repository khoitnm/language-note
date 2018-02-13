var $y = (function (module) {
        module.getEmbedUrl = function (url) {
            var youtubeId = this.getYoutubeId(url);
            var embedUrl = "http://www.youtube.com/embed/" + youtubeId + "/";
            return embedUrl;
        };
        module.getYoutubeId = function (url) {
            var videoId = url.split('v=')[1];
            var ampersandPosition = videoId.indexOf('&');
            if (ampersandPosition != -1) {
                videoId = videoId.substring(0, ampersandPosition);
            }
            return videoId;
        };
        module.getYoutubeThumbnails = function (url) {
            var youtubeId = this.getYoutubeId(url);
            var thumbnailUrlPrefix = "https://img.youtube.com/vi/" + youtubeId;
            var thumbnailUrls = {
                defaultUrl: thumbnailUrlPrefix + "/default.jpg"
                , hqdefaultUrl: thumbnailUrlPrefix + "/hqdefault.jpg"
                , mqdefaultUrl: thumbnailUrlPrefix + "/mqdefault.jpg"

                //Below are missed sometimes, depend on the video.
                , sddefaultUrl: thumbnailUrlPrefix + "/sddefault.jpg"
                , maxresdefaultUrl: thumbnailUrlPrefix + "/maxresdefault.jpg"
            };
            return thumbnailUrls;
        };
    }($y || {})
);