CREATE OR REPLACE FUNCTION jb_to_ta(jsonb) RETURNS text[] as $$
DECLARE
    res text[];
BEGIN
    res = (select array_agg(x) from jsonb_array_elements_text( $1 ) x);
    return res;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ta_to_jb(text[]) RETURNS jsonb as $$
BEGIN
    return array_to_json($1);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW Cars_for_java AS
SElECT
    *,
    jb_to_ta(c.configuration->'features') AS features
FROM cars as c;

CREATE OR REPLACE FUNCTION update_view_cars_for_java() RETURNS trigger
AS $update_view_cars_for_java$
BEGIN
	IF (TG_OP = 'DELETE') THEN
	    DELETE FROM cars WHERE car_id = OLD.car_id;
	    IF NOT FOUND THEN
			RETURN NULL;
        END IF;
	    RETURN OLD;
    ELSEIF (TG_OP = 'UPDATE') THEN
	    IF NEW.car_id IS NULL THEN
            UPDATE Cars_for_java SET
                   car_id = DEFAULT,
                   configuration = jsonb_set(configuration, 'features', ta_to_jb(NEW.features), true),
                   model_id = NEW.model_id,
                   left_steering_wheel = NEW.left_steering_wheel,
                   mileage = NEW.mileage,
                   price = NEW.price,
                   transmission = NEW.transmission,
                   drive = NEW.drive,
                   fuel = NEW.fuel
            WHERE car_id = OLD.car_id;
        ELSE
            UPDATE Cars_for_java SET
                    car_id = NEW.car_id,
                    configuration = jsonb_set(configuration, 'features', ta_to_jb(NEW.features), true),
                    model_id = NEW.model_id,
                    left_steering_wheel = NEW.left_steering_wheel,
                    mileage = NEW.mileage,
                    price = NEW.price,
                    transmission = NEW.transmission,
                    drive = NEW.drive,
                    fuel = NEW.fuel
            WHERE car_id = OLD.device_id;
        END IF;
		IF NOT FOUND THEN
			RETURN NULL;
        END IF;
        RETURN NEW;
    ELSEIF (TG_OP = 'INSERT') THEN
		IF NEW.device_id IS NULL THEN
			INSERT INTO Cars_for_java(model_id, configuration, left_steering_wheel, mileage, price, transmission, drive, fuel) VALUES
				(NEW.model_id, jsonb_set(NEW.configuration, 'features', ta_to_jb(NEW.features), true),);
        ELSE
			INSERT INTO Cars_for_java(car_id, name, add_info) VALUES
				(NEW.device_id, NEW.name, json_build_object('CPU', NEW.CPU, 'RAM', NEW.RAM));
        END IF;
        RETURN NEW;
    ELSE
		RETURN NULL;
    END IF;
    RETURN NULL;
END;
$update_view_cars_for_java$
LANGUAGE plpgsql;

CREATE TRIGGER devices_view_update
    INSTEAD OF INSERT OR UPDATE OR DELETE ON devices
    FOR EACH ROW EXECUTE FUNCTION update_view_devices();


CREATE OR REPLACE FUNCTION update_devices() RETURNS trigger
AS $update_devices$
DECLARE
c type_of_arch;
	r smallint;
BEGIN
	c = NEW.add_info->>'CPU'::type_of_arch;
	r = NEW.add_info->>'RAM'::smallint;
RETURN NEW;
EXCEPTION WHEN invalid_text_representation THEN
	RAISE EXCEPTION 'invalid input value for devices for CPU % or for RAM %', NEW.add_info->>'CPU',  NEW.add_info->>'RAM';
END;
$update_devices$
LANGUAGE plpgsql;

CREATE TRIGGER devices_update
    BEFORE INSERT OR UPDATE OR DELETE ON devices_with_additinal_info
    FOR EACH ROW EXECUTE FUNCTION update_devices(); */

