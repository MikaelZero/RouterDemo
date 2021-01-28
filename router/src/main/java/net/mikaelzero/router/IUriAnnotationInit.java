package net.mikaelzero.router;


public interface IUriAnnotationInit extends AnnotationInit<UriHandler> {

    @Override
    void init(UriHandler handler);
}
