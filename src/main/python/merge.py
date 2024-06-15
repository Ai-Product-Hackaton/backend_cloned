import sys
from PIL import Image

def open_image(image_path):
    image = Image.open(image_path)
    image = image.convert("RGB")
    return image

def merge(back, obj, cord_x, cord_y):
    back = open_image(back)
    obj = open_image(obj)
    back.paste(obj, (int(cord_x), int(cord_y)))
    back.save('merged.jpg', quality=100)
    obj.close()
    back.close()
    return back

if __name__ == "__main__":
    if len(sys.argv) != 5:
        print("Usage: python merge.py <path_to_background> <path_to_object> <cord_x> <cord_y>")
        sys.exit(1)

    back_path = sys.argv[1]
    object_path = sys.argv[2]
    cord_x = sys.argv[3]
    cord_y = sys.argv[4]
    merge(back_path, object_path, cord_x, cord_y)
    print("done)")




